package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee update(Integer id, Employee employeeDetails) throws ResourceNotFoundException {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        updatedEmployee.setFirstName(employeeDetails.getFirstName());
        updatedEmployee.setLastName(employeeDetails.getLastName());
        updatedEmployee.setGender(employeeDetails.getGender());
        updatedEmployee.setEmail(employeeDetails.getEmail());
        updatedEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
        updatedEmployee.setAddress(employeeDetails.getAddress());
        updatedEmployee.setEmployeeType(employeeDetails.getEmployeeType());
        updatedEmployee.setManagerId(employeeDetails.getManagerId());
        updatedEmployee.setEmployeeStatus(employeeDetails.getEmployeeStatus());
        return employeeRepository.save(updatedEmployee);
    }
}
