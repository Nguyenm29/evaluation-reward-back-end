package com.vis.backend.service;

import com.vis.backend.model.response.RewardPointResponse;
import com.vis.backend.model.response.TimeKeepingResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PointService {

    public Optional<List<RewardPointResponse>> calPoint(MultipartFile file) throws IOException;

    public Optional<Boolean> save(List<RewardPointResponse> list);

    public Optional<Float> getRewardPoint(String employeeId);

    public Optional<Boolean> exchangePoint(String cost, String employeeId, String serviceId);
}
