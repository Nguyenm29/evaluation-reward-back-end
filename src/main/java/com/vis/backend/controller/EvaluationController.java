package com.vis.backend.controller;

import com.vis.backend.service.EvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/back/evaluation")
public class EvaluationController extends AbstractController<EvaluationService>{

    @GetMapping("")
    public ResponseEntity<?> getEvaluation(@RequestParam("employeeId") String employeeId) {
        return response(service.getEvaluationByEmployeeId(employeeId));
    }
}
