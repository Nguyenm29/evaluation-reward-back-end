package com.vis.backend.service;

import com.vis.backend.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findEmployeeByEmployeeId(Long employeeId);
}
