package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.CustomerRepository;
import com.axonactive.phonestore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer update(Integer id, Customer customerDetails) throws ResourceNotFoundException {
        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
        updatedCustomer.setFullName(customerDetails.getFullName());
        updatedCustomer.setAddress(customerDetails.getAddress());
        updatedCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        return customerRepository.save(updatedCustomer);
    }

    @Override
    public List<Customer> findByPhoneNumberContaining(String phoneNumber) {
        return customerRepository.findByPhoneNumberContaining(phoneNumber);
    }

    @Override
    public List<Customer> findByFullNameContaining(String name) {
        return customerRepository.findByFullNameContaining(name);
    }
}
