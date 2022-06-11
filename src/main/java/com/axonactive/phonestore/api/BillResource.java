package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.CustomerService;
import com.axonactive.phonestore.service.EmployeeService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import com.axonactive.phonestore.service.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(BillResource.PATH)
public class BillResource {
    public static final String PATH = "/api/bills";
    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BillDetailService billDetailService;

    @GetMapping
    public ResponseEntity<List<BillDto>> getAll() {
        List<Bill> bills = billService.getAll();
        List<BillDto> billDtos = billMapper.toDtos(bills);
        for (BillDto billDto : billDtos) {
            billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(billDto.getId())));
        }
        return ResponseEntity.ok(billDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> findBillById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Bill bill = billService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id));
        BillDto billDto = billMapper.toDto(bill);
        billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(id)));
        return ResponseEntity.ok(billDto);
    }

    @PostMapping
    public ResponseEntity<BillDto> add(@RequestBody BillRequest billRequest) throws ResourceNotFoundException {
        Bill bill = new Bill();
        bill.setCustomer(customerService.findById(billRequest.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        bill.setEmployee(employeeService.findById(billRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        Bill createdBill = billService.save(bill);
        return ResponseEntity.created(URI.create(BillResource.PATH + "/" + createdBill.getId())).body(billMapper.toDto(createdBill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> update(@PathVariable(value = "id") Integer id, @RequestBody BillRequest billRequest) throws ResourceNotFoundException {
        Bill bill = new Bill();
        bill.setCustomer(customerService.findById(billRequest.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        bill.setEmployee(employeeService.findById(billRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        return ResponseEntity.ok(billMapper.toDto(billService.update(id, bill)));
    }
}
