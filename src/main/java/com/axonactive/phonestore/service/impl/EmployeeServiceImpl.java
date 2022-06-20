package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import com.axonactive.phonestore.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public List<EmployeeDto> getAll() {
        return employeeMapper.toDtos(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto findById(Integer id) throws EntityNotFoundException {
        return employeeMapper.toDto(employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id)));
    }

    @Override
    public EmployeeDto save(EmployeeRequest employeeRequest) throws EntityNotFoundException {
        Employee createdEmployee = new Employee();
        createdEmployee.setFirstName(employeeRequest.getFirstName());
        createdEmployee.setLastName(employeeRequest.getLastName());
        createdEmployee.setGender(employeeRequest.getGender());
        createdEmployee.setEmail(employeeRequest.getEmail());
        createdEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        createdEmployee.setAddress(employeeRequest.getAddress());
        createdEmployee.setEmployeeType(employeeRequest.getEmployeeType());
        createdEmployee.setEmployeeStatus(employeeRequest.getEmployeeStatus());
        createdEmployee.setManagerId(employeeRequest.getManagerId());
        createdEmployee.setStore(storeRepository.findById(employeeRequest.getStoreId()).orElseThrow(() -> new EntityNotFoundException("Store not found: " + employeeRequest.getStoreId())));
        return employeeMapper.toDto(employeeRepository.save(createdEmployee));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto update(Integer id, EmployeeRequest employeeRequest) throws EntityNotFoundException {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        updatedEmployee.setFirstName(employeeRequest.getFirstName());
        updatedEmployee.setLastName(employeeRequest.getLastName());
        updatedEmployee.setGender(employeeRequest.getGender());
        updatedEmployee.setEmail(employeeRequest.getEmail());
        updatedEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        updatedEmployee.setAddress(employeeRequest.getAddress());
        updatedEmployee.setEmployeeType(employeeRequest.getEmployeeType());
        updatedEmployee.setManagerId(employeeRequest.getManagerId());
        updatedEmployee.setEmployeeStatus(employeeRequest.getEmployeeStatus());
        updatedEmployee.setStore(storeRepository.findById(employeeRequest.getStoreId()).orElseThrow(() -> new EntityNotFoundException("Store not found: " + employeeRequest.getStoreId())));

        return employeeMapper.toDto(employeeRepository.save(updatedEmployee));
    }
}
