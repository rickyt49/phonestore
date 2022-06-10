package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.EmployeeAccountRequest;
import com.axonactive.phonestore.entity.EmployeeAccount;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.EmployeeAccountService;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.EmployeeAccountDto;
import com.axonactive.phonestore.service.mapper.EmployeeAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(EmployeeAccountResource.PATH)
public class EmployeeAccountResource {
    public static final String PATH = "/api/employeeaccounts";
    @Autowired
    EmployeeAccountService employeeAccountService;
    @Autowired
    EmployeeAccountMapper employeeAccountMapper;

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeAccountDto>> getAll() {
        return ResponseEntity.ok(employeeAccountMapper.toDtos(employeeAccountService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAccountDto> findEmployeeAccountById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeAccountMapper.toDto(employeeAccountService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Account not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<EmployeeAccountDto> add(@RequestBody EmployeeAccountRequest employeeAccountRequest) throws ResourceNotFoundException {
        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setUsername(employeeAccountRequest.getUsername());
        employeeAccount.setPassword(employeeAccountRequest.getPassword());
        employeeAccount.setEmployee(employeeService.findById(employeeAccountRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found " + employeeAccountRequest.getEmployeeId())));
        EmployeeAccount createdEmpAccount = employeeAccountService.save(employeeAccount);
        return ResponseEntity.created(URI.create(EmployeeAccountResource.PATH + "/" + createdEmpAccount.getId())).body(employeeAccountMapper.toDto(createdEmpAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        employeeAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeAccountDto> update(@PathVariable(value = "id") Integer id, @RequestBody EmployeeAccountRequest employeeAccountRequest) throws ResourceNotFoundException {
        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setUsername(employeeAccountRequest.getUsername());
        employeeAccount.setPassword(employeeAccountRequest.getPassword());
        employeeAccount.setEmployee(employeeService.findById(employeeAccountRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found " + employeeAccountRequest.getEmployeeId())));
        return ResponseEntity.ok(employeeAccountMapper.toDto(employeeAccountService.update(id, employeeAccount)));
    }
}
