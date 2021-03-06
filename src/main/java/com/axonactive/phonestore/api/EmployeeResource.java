package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import com.axonactive.phonestore.service.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(EmployeeResource.PATH)
public class EmployeeResource {
    public static final String PATH = "/api/employees";
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BillService billService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable(value = "id") Integer id)  {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> add(@Valid @RequestBody EmployeeRequest employeeRequest)  {
        EmployeeDto createdEmployee = employeeService.save(employeeRequest);
        return ResponseEntity.created(URI.create(EmployeeResource.PATH + "/" + createdEmployee.getId())).body(createdEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable(value = "id") Integer id,@Valid @RequestBody EmployeeRequest employeeRequest)  {
        return ResponseEntity.ok(employeeService.update(id, employeeRequest));
    }


    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillDto>> getBillByEmployeeAndSaleDate(@PathVariable(value = "id") Integer id, @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeId(id));
        }
        if (endDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateAfter(id, startDate));
        }
        if (startDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBefore(id, endDate));
        }
        return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBetween(id, startDate, endDate));
    }

    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDto>> getActiveEmployee() {
        return ResponseEntity.ok(employeeService.getAllActiveEmployee());
    }
}
