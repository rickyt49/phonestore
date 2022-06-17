package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.EmployeeRequest;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import com.axonactive.phonestore.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(EmployeeResource.PATH)
public class EmployeeResource {
    public static final String PATH = "/api/employees";
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private BillService billService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> add(@RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        EmployeeDto createdEmployee = employeeService.save(employeeRequest);
        return ResponseEntity.created(URI.create(EmployeeResource.PATH + "/" + createdEmployee.getId())).body(createdEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable(value = "id") Integer id, @RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(employeeService.update(id, employeeRequest));
    }


    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillDto>> getBillByEmployeeAndSaleDate(@PathVariable(value = "id") Integer id, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) {
        if (startDate == null && endDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeId(id));
        }
        if (endDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateAfter(id, LocalDate.parse(startDate)));
        }
        if (startDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBefore(id, LocalDate.parse(endDate)));
        }
        return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBetween(id, LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }
}
