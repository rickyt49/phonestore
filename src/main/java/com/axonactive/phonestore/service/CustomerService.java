package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.CustomerRequest;
import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAll();

    CustomerDto findById(Integer id) throws ResourceNotFoundException;

    CustomerDto save(CustomerRequest customerRequest);

    void delete(Integer id);

    CustomerDto update(Integer id, CustomerRequest customerRequest) throws ResourceNotFoundException;

    List<CustomerDto> findByPhoneNumberContaining(String phoneNumber);

    List<CustomerDto> findByFullNameContaining(String name);
}
