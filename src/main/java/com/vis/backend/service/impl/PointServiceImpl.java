package com.vis.backend.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.vis.backend.entity.*;
import com.vis.backend.model.response.RewardPointResponse;
import com.vis.backend.model.response.TimeKeepingResponse;
import com.vis.backend.repository.*;
import com.vis.backend.service.EmployeeService;
import com.vis.backend.service.PointService;
import com.vis.backend.util.DateTimeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class PointServiceImpl implements PointService {

    private final String LAST_TIME_IN = "08:10";
    private final String LAST_TIME_OUT = "17:00";
    private final String DATE_TIME_FM_TYPE_ONE = "HH:mm";
    private final String DATE_TIME_FM_TYPE_TWO = "H:mm";
    private final float POINT_RATE_OVER_TIME = 10F;
    private final float POINT_RATE_DAY_WORD = 10F;
    private final String REST_DAY ="x";
    private final float POINT_RATE_LATE = 5;

    @Autowired
    private EvaluationHistoryRepository evaluationHistoryRepository;

    @Autowired
    private EvaluationPointRepository evaluationPointRepository;

    @Autowired
    private RewardHistoryRepository rewardHistoryRepository;

    @Autowired
    private RewardPointRepository rewardPointRepository;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Optional<List<RewardPointResponse>> calPoint(MultipartFile file) throws IOException {
        Reader fileReader = new InputStreamReader(file.getInputStream());
        CSVReader reader = new CSVReaderBuilder(fileReader).withSkipLines(1).withSkipLines(2).build();
        String [] nextLine;
        List<TimeKeepingResponse> listTimeKeeping = new ArrayList();

        while ((nextLine = reader.readNext()) != null) {
            TimeKeepingResponse timeKeeping = new TimeKeepingResponse();
            timeKeeping.setEmployeeId(nextLine[1]);
            timeKeeping.setName(nextLine[2]);

            if (!StringUtils.isEmpty(nextLine[nextLine.length -1])) {
                timeKeeping.setOverTime(Integer.parseInt(nextLine[nextLine.length - 1]));
            } else {
                timeKeeping.setOverTime(0);
            }
            float[] workDays = new float[31];
            int j = 3;
            int k = 4;
            for (int i = 0; i < workDays.length; i++) {
                LocalTime out = null;
                LocalTime in = null;
                float work = 1;

                if (!nextLine[k].equals(REST_DAY) && !nextLine[j].equals(REST_DAY)) {
                    if (!StringUtils.isEmpty(nextLine[k])) {
                        try {
                            out = LocalTime.parse(nextLine[k], DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_TWO));
                        } catch (Exception e) {
                            out = LocalTime.parse(nextLine[k], DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE));
                        }
                    } else {
                        work -= 0.5f;
                    }

                    if (!StringUtils.isEmpty(nextLine[j])) {
                        try {
                            in = LocalTime.parse(nextLine[j], DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_TWO));
                        } catch (Exception e) {
                            in = LocalTime.parse(nextLine[j], DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE));
                        }
                    } else {
                        work -= 0.5f;
                    }
                    if (work == 0) {
                        workDays[i] = 0;
                    } else {
                        workDays[i] = work - calLateAndEarly(in, out);
                    }
                } else {
                    workDays[i] = -1;
                }
                j += 2;
                k += 2;
            }

            timeKeeping.setWorkDays(workDays);
            listTimeKeeping.add(timeKeeping);
        }
        List<String> listEmail = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (TimeKeepingResponse item : listTimeKeeping) {
            ids.add(Long.valueOf(item.getEmployeeId()));
        }
        List<Employee> listEmployee = employeeRepository.findAllById(ids);
        return Optional.ofNullable(calRewardPoint(listTimeKeeping));
    }

    @Override
    public Optional<Boolean> save(List<RewardPointResponse> list) {
        boolean isSaveRewardPoint = saveRewardPoint(list);
        if (!isSaveRewardPoint) {
            return Optional.of(false);
        }
        boolean isSaveEvaluationPoint = saveEvaluationPoint(list);
        if (!isSaveEvaluationPoint) {
            return Optional.of(false);
        }
        boolean isSaveEvaluation = saveEvaluation(list);
        if (!isSaveEvaluation) {
            return Optional.of(false);
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Float> getRewardPoint(String employeeId) {
        Optional<Float> point = rewardPointRepository.getRewardPoint(Long.valueOf(employeeId));
        return point;
    }

    @Override
    public Optional<Boolean> exchangePoint(String cost, String employeeId, String serviceId) {

        RewardPoint rewardPoint = RewardPoint.builder()
                .employeeId(Long.valueOf(employeeId))
                .point(-Float.valueOf(cost))
                .createdAt(dateTimeUtil.getCurrentTimeStamp())
                .modifiedAt(dateTimeUtil.getCurrentTimeStamp())
                .modifiedBy(employeeId)
                .createdBy(employeeId)
                .date(dateTimeUtil.getCurrentTimeStamp())
                .build();
        RewardPoint optionalRewardPoint = rewardPointRepository.save(rewardPoint);
        if (ObjectUtils.isEmpty(optionalRewardPoint)) {
            return Optional.of(false);
        }
        RewardHistory rewardHistory = RewardHistory.builder()
                .employeeId(Long.valueOf(employeeId))
                .createdAt(dateTimeUtil.getCurrentTimeStamp())
                .modifiedAt(dateTimeUtil.getCurrentTimeStamp())
                .modifiedBy(employeeId)
                .createdBy(employeeId)
                .pointUse(Float.valueOf(cost))
                .date(dateTimeUtil.getCurrentTimeStamp())
                .serviceId(Long.valueOf(serviceId))
                .build();
        RewardHistory history = rewardHistoryRepository.save(rewardHistory);
        if (ObjectUtils.isEmpty(history)) {
            return Optional.of(false);
        }
        return Optional.of(true);
    }

    private boolean saveRewardPoint(List<RewardPointResponse> list) {
        List<RewardPoint> listRewardPoint = new ArrayList<>();
        for (RewardPointResponse item : list) {
            RewardPoint rewardPoint = RewardPoint.builder()
                    .point(item.getTotalPoint())
                    .createdAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedBy("admin")
                    .createdBy("admin")
                    .employeeId(Long.valueOf(item.getEmployeeID()))
                    .date(dateTimeUtil.getCurrentTimeStamp())
                    .build();

            listRewardPoint.add(rewardPoint);
        }
        List<RewardPoint> result = rewardPointRepository.saveAll(listRewardPoint);
        return result.isEmpty() ? false : true;
    }

    private boolean saveEvaluationPoint(List<RewardPointResponse> list) {
        List<EvaluationPoint> pointList = new ArrayList<>();
        for (RewardPointResponse item : list) {
            EvaluationPoint point = EvaluationPoint.builder()
                    .point(item.getTotalPoint())
                    .createdAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedBy("admin")
                    .createdBy("admin")
                    .employeeId(Long.valueOf(item.getEmployeeID()))
                    .date(dateTimeUtil.getCurrentTimeStamp())
                    .build();

            pointList.add(point);
        }
        List<EvaluationPoint> result = evaluationPointRepository.saveAll(pointList);
        return result.isEmpty() ? false : true;
    }

    private boolean saveEvaluation(List<RewardPointResponse> list) {
        List<EvaluationPoint> evaluations = new ArrayList<>();
        for (RewardPointResponse item : list) {
            EvaluationPoint evaluation = EvaluationPoint.builder()
                    .dayOff(item.getDayLeft())
                    .dayWork(item.getDayWork())
                    .lateTime(item.getTimeLate())
                    .createdAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedAt(dateTimeUtil.getCurrentTimeStamp())
                    .modifiedBy("admin")
                    .createdBy("admin")
                    .employeeId(Long.valueOf(item.getEmployeeID()))
                    .date(dateTimeUtil.getCurrentTimeStamp())
                    .overTime(item.getOverTime())
                    .point(item.getTotalPoint())
                    .build();

            evaluations.add(evaluation);
        }
        List<EvaluationPoint> result = evaluationPointRepository.saveAll(evaluations);
        return result.isEmpty() ? false : true;
    }
    private List calRewardPoint(List<TimeKeepingResponse> list) {
        List listReward = new ArrayList<RewardPointResponse>();
        for (TimeKeepingResponse item: list)  {
            RewardPointResponse response = RewardPointResponse.builder()
                    .employeeID(item.getEmployeeId())
                    .name(item.getName())
                    .overTimeRate(POINT_RATE_OVER_TIME)
                    .workDayRate(POINT_RATE_DAY_WORD)
                    .timeLateRate(POINT_RATE_LATE)
                    .overTime(item.getOverTime())
                    .build();
            float workDay = 0;
            float leftDay = 0;
            int timeLate = 0;
            for (float i:item.getWorkDays()) {
                if (i != -1){
                    if (i >= 0 && i <= 0.5) {
                        workDay+=0.5;
                        if (i < 0.5) {
                            timeLate +=1;
                        }
                        if (i == 0) {
                            leftDay +=1;
                        } else {
                            leftDay +=0.5;
                        }
                    }
                    if (i <= 1 && i > 0.5) {
                        workDay+=1;
                        if (i == 0.9) {
                            timeLate +=1;
                        } else if (i == 0.8) {
                            timeLate +=2;
                        }
                    }
                }
            }
            response.setDayWork(workDay);
            response.setTimeLate(timeLate);
            response.setDayLeft(leftDay);
            response.setTotalPoint(
                    workDay * response.getWorkDayRate()
                            + response.getOverTime() * response.getOverTimeRate()
                            - response.getTimeLate() * response.getTimeLateRate());
            listReward.add(response);
        }
        return listReward;
    }

    private float calLateAndEarly(LocalTime in, LocalTime out) {
        float work = 0;
        if (ObjectUtils.isEmpty(in)) {
            if (MINUTES.between(LocalTime.parse(LAST_TIME_OUT, DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE)), out) < 0) {
                work += 0.1;
            }
        } else if (ObjectUtils.isEmpty(out)) {
            if (MINUTES.between(in, LocalTime.parse(LAST_TIME_IN, DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE))) < 0) {
                work +=0.1;
            }
        } else {
            if (MINUTES.between(in, LocalTime.parse(LAST_TIME_IN, DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE))) < 0) {
                work +=0.1;
            }
            if (MINUTES.between(LocalTime.parse(LAST_TIME_OUT, DateTimeFormatter.ofPattern(DATE_TIME_FM_TYPE_ONE)), out) < 0) {
                work +=0.1;
            }
        }
        return work;
    }
}
