package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import com.axonactive.phonestore.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping(EmployeeResource.PATH)
public class EmployeeResource {
    public static final String PATH = "/api/employees";
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StoreService storeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(employeeMapper.toDtos(employeeService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeMapper.toDto(employeeService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> add(@RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        Employee createdEmployee = employeeService.save(new Employee(null, employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getGender(), employeeRequest.getEmail(), employeeRequest.getPhoneNumber(), employeeRequest.getAddress(), employeeRequest.getEmployeeType(), employeeRequest.getEmployeeStatus(), employeeRequest.getManagerId(), storeService.findById(employeeRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + employeeRequest.getStoreId()))));
        return ResponseEntity.created(URI.create(EmployeeResource.PATH + "/" + createdEmployee.getId())).body(employeeMapper.toDto(createdEmployee));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id")
    public ResponseEntity<EmployeeDto> update(@PathVariable(value = "id") Integer id, @RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        Employee updatedEmployee = employeeService.update(id, new Employee(
                null,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getGender(),
                employeeRequest.getEmail(),
                employeeRequest.getPhoneNumber(),
                employeeRequest.getAddress(),
                employeeRequest.getEmployeeType(),
                employeeRequest.getEmployeeStatus(),
                employeeRequest.getManagerId(),
                storeService.findById(employeeRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + employeeRequest.getStoreId()))));
        return ResponseEntity.ok(employeeMapper.toDto(updatedEmployee));
    }
}
