package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAll();

    Optional<Customer> findById(Integer id);

    Customer save(Customer customer);

    void delete(Integer id);

    Customer update(Integer id, Customer customerDetails) throws ResourceNotFoundException;

    List<Customer> findByPhoneNumberContaining(String phoneNumber);

    List<Customer> findByFullNameContaining(String name);
}
