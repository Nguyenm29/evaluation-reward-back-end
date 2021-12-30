package com.vis.backend.service.impl;

import com.vis.backend.entity.EvaluationPoint;
import com.vis.backend.model.response.EvaluationResponse;
import com.vis.backend.repository.EvaluationPointRepository;
import com.vis.backend.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationPointRepository evaluationPointRepository;

    @Override
    public Optional<List<EvaluationResponse>> getEvaluationByEmployeeId(String employeeId) {
        List<EvaluationResponse> evaluationResponses = new ArrayList<>();
        Optional<List<EvaluationPoint>> evaluationPoints = evaluationPointRepository.findAllByEmployeeId(Long.valueOf(employeeId));;

        List<EvaluationPoint> rsEvaluation = evaluationPoints.get();
        rsEvaluation.sort(Comparator.comparing(o -> o.getDate()));
        if (evaluationPoints.isEmpty()) {
            return Optional.empty();
        }
        for (EvaluationPoint item : evaluationPoints.get()) {
            EvaluationResponse evaluationResponse = EvaluationResponse.builder()
                    .overTime(item.getOverTime())
                    .dayWork(item.getDayWork())
                    .dayOff(item.getDayOff())
                    .lateTime(item.getLateTime())
                    .month(new SimpleDateFormat("MM-yyyy").format(item.getDate()))
                    .totalPoint(item.getPoint())
                    .build();
            evaluationResponses.add(evaluationResponse);
        }
        return Optional.of(evaluationResponses);
    }
}
