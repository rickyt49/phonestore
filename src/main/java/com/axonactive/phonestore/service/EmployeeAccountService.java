package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.EmployeeAccount;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeAccountService {
    List<EmployeeAccount> getAll();

    Optional<EmployeeAccount> findById(Integer id);

    EmployeeAccount save(EmployeeAccount employeeAccount);

    void delete(Integer id);

    EmployeeAccount update(Integer id, EmployeeAccount employeeAccountDetails) throws ResourceNotFoundException;

}
