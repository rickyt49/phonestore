package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.EmployeeAccount;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.EmployeeAccountRepository;
import com.axonactive.phonestore.service.EmployeeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeAccountServiceImpl implements EmployeeAccountService {

    @Autowired
    EmployeeAccountRepository employeeAccountRepository;

    @Override
    public List<EmployeeAccount> getAll() {
        return employeeAccountRepository.findAll();
    }

    @Override
    public Optional<EmployeeAccount> findById(Integer id) {
        return employeeAccountRepository.findById(id);
    }

    @Override
    public EmployeeAccount save(EmployeeAccount employeeAccount) {
        return employeeAccountRepository.save(employeeAccount);
    }

    @Override
    public void delete(Integer id) {
        employeeAccountRepository.deleteById(id);
    }

    @Override
    public EmployeeAccount update(Integer id, EmployeeAccount employeeAccountDetails) throws ResourceNotFoundException {
        EmployeeAccount updatedEmployeeAccount = employeeAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Account not found: " + id));
        updatedEmployeeAccount.setUsername(employeeAccountDetails.getUsername());
        updatedEmployeeAccount.setPassword(employeeAccountDetails.getPassword());
        return employeeAccountRepository.save(updatedEmployeeAccount);
    }
}
