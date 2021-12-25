package com.vis.backend.service;

import com.vis.backend.model.request.UserRequest;
import com.vis.backend.model.response.UserResponse;

import java.util.Optional;

public interface LoginService {

    public Optional<UserResponse> login(UserRequest userRequest);
}
