package com.vis.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long employeeId;
    private String name;
    private Integer role;
    private String view;
}
