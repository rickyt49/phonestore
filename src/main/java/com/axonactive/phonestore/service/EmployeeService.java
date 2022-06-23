package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto findById(Integer id) ;

    EmployeeDto save(EmployeeRequest employeeRequest) ;

    void delete(Integer id);

    EmployeeDto update(Integer id, EmployeeRequest employeeRequest) ;

    List<EmployeeDto> getAllActiveEmployee();
}
