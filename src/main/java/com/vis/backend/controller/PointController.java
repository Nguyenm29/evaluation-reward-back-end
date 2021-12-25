package com.vis.backend.controller;

import com.vis.backend.model.response.RewardPointResponse;
import com.vis.backend.service.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("back")
public class PointController extends AbstractController<PointService> {

    @PostMapping("/admin/point/cal")
    public ResponseEntity<?> cal(HttpServletRequest httpRequest, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        return response(service.calPoint(file));
    }

    @PostMapping("/admin/point/save")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody List<RewardPointResponse> list) {
        return response(service.save(list));
    }

    @GetMapping("/point/reward")
    public ResponseEntity<?> getPointReward(@RequestParam(value = "employeeId", required = false) String employeeId) {
        return response(service.getRewardPoint(employeeId));
    }
}
