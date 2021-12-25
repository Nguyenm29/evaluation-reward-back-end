package com.vis.backend.service.impl;

import com.vis.backend.entity.User;
import com.vis.backend.model.request.UserRequest;
import com.vis.backend.model.response.UserResponse;
import com.vis.backend.repository.UserRepository;
import com.vis.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserResponse> login(UserRequest userRequest) {
        Optional<User> user = userRepository.findUserByUserNameAndPassword(userRequest.getUsername(), userRequest.getPassword());
        if (!user.isPresent()) {
            return null;
        }
        return Optional.of(UserResponse.builder().name(user.get().getUserName()).role(user.get().getAuthority()).employeeId(user.get().getEmployeeId()).build());
    }
}
