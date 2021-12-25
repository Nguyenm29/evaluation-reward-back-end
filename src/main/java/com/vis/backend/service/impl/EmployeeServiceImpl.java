package com.vis.backend.service.impl;

import com.vis.backend.entity.Employee;
import com.vis.backend.repository.EmployeeRepository;
import com.vis.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> findEmployeeByEmployeeId(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
