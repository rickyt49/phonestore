package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.CustomerRequest;
import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
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
        return ResponseEntity.ok(customerMapper.toDtos(customerService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerMapper.toDto(customerService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> add(@RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setAddress(customerRequest.getAddress());
        customer.setFullName(customerRequest.getFullName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        Customer createdCustomer = customerService.save(customer);
        return ResponseEntity.created(URI.create(CustomerResource.PATH + "/" + createdCustomer.getId())).body(customerMapper.toDto(createdCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable(value = "id") Integer id, @RequestBody CustomerRequest customerRequest) throws ResourceNotFoundException {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setAddress(customerRequest.getAddress());
        updatedCustomer.setFullName(customerRequest.getFullName());
        updatedCustomer.setPhoneNumber(customerRequest.getPhoneNumber());
        return ResponseEntity.ok(customerMapper.toDto(customerService.update(id, updatedCustomer)));
    }

//    @GetMapping("/where")
//    public ResponseEntity<List<CustomerDto>> findCustomerByPhoneNumber(@RequestParam(value = "phonenumber") String phoneNumber) {
//        return ResponseEntity.ok(customerMapper.toDtos(customerService.findByPhoneNumberContaining(phoneNumber)));
//    }

//    @GetMapping("/where")
//    public ResponseEntity<List<CustomerDto>> findCustomerByFullName(@RequestParam(value = "name") String name) {
//        return ResponseEntity.ok(customerMapper.toDtos(customerService.findByFullNameContaining(name)));
//    }


}
