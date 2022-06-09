package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAll();

    Optional<Employee> findById(Integer id);

    Employee save(Employee employee);

    void delete(Integer id);

    Employee update(Integer id, Employee employeeDetails) throws ResourceNotFoundException;
}
