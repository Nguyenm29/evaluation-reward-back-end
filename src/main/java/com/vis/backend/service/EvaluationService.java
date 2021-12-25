package com.vis.backend.service;

import com.vis.backend.model.response.EvaluationResponse;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {

    Optional<List<EvaluationResponse>> getEvaluationByEmployeeId(String employeeId);
}
