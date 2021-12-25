package com.vis.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private java.sql.Timestamp modifiedAt;
    private java.sql.Timestamp createdAt;
    private String createdBy;
    private String modifiedBy;
    private String name;
    private Integer age;
    private String phone;
    private String paymentInfo;
    private String email;
}
