package com.vis.backend.service.impl;

import com.vis.backend.model.response.EvaluationResponse;
import com.vis.backend.repository.EvaluationPointRepository;
import com.vis.backend.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationPointRepository evaluationPointRepository;

    @Override
    public Optional<List<EvaluationResponse>> getEvaluationByEmployeeId(String employeeId) {
        return Optional.empty();
    }
}
