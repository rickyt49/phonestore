package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import com.axonactive.phonestore.service.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
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
        return employeeMapper.toDto(employeeRepository.findById(id).orElseThrow(BusinessLogicException::EmployeeNotFound));
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
        createdEmployee.setStore(storeRepository.findById(employeeRequest.getStoreId()).orElseThrow(BusinessLogicException::EmployeeNotFound));
        return employeeMapper.toDto(employeeRepository.save(createdEmployee));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto update(Integer id, EmployeeRequest employeeRequest) throws EntityNotFoundException {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(BusinessLogicException::EmployeeNotFound);
        updatedEmployee.setFirstName(employeeRequest.getFirstName());
        updatedEmployee.setLastName(employeeRequest.getLastName());
        updatedEmployee.setGender(employeeRequest.getGender());
        updatedEmployee.setEmail(employeeRequest.getEmail());
        updatedEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        updatedEmployee.setAddress(employeeRequest.getAddress());
        updatedEmployee.setEmployeeType(employeeRequest.getEmployeeType());
        updatedEmployee.setManagerId(employeeRequest.getManagerId());
        updatedEmployee.setEmployeeStatus(employeeRequest.getEmployeeStatus());
        updatedEmployee.setStore(storeRepository.findById(employeeRequest.getStoreId()).orElseThrow(BusinessLogicException::StoreNotFound));

        return employeeMapper.toDto(employeeRepository.save(updatedEmployee));
    }

    @Override
    public List<EmployeeDto> getAllActiveEmployee() {
        return employeeMapper.toDtos(employeeRepository.getAllActiveEmployee());
    }

}
