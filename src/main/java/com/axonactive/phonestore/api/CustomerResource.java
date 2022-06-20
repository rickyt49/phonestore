package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.CustomerRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.CustomerService;
import com.axonactive.phonestore.service.dto.CustomerDto;
import com.axonactive.phonestore.service.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(CustomerResource.PATH)
public class CustomerResource {
    public static final String PATH = "/api/customers";

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> add(@RequestBody CustomerRequest customerRequest) {
        CustomerDto createdCustomer = customerService.save(customerRequest);
        return ResponseEntity.created(URI.create(CustomerResource.PATH + "/" + createdCustomer.getId())).body(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable(value = "id") Integer id, @RequestBody CustomerRequest customerRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(customerService.update(id, customerRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDto>> findCustomerByPhoneNumber(@RequestParam(value = "phoneNumber", required = false) String phoneNumber, @RequestParam(value = "name", required = false) String name) {
        if (null == name && null != phoneNumber) {
            return ResponseEntity.ok(customerService.findByPhoneNumberContaining(phoneNumber));
        }
        if (null == phoneNumber && null != name) {
            return ResponseEntity.ok(customerService.findByFullNameContaining(name));
        }
        return ResponseEntity.ok(customerService.getAll());
    }


}
