package com.vis.backend.controller;

import com.vis.backend.model.request.UserRequest;
import com.vis.backend.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/login")
public class LoginController extends AbstractController<LoginService> {

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        return response(service.login(userRequest));
    }
}
