package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto findById(Integer id) throws EntityNotFoundException;

    EmployeeDto save(EmployeeRequest employeeRequest) throws EntityNotFoundException;

    void delete(Integer id);

    EmployeeDto update(Integer id, EmployeeRequest employeeRequest) throws EntityNotFoundException;
}
