package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.CustomerRequest;
import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.CustomerRepository;
import com.axonactive.phonestore.service.CustomerService;
import com.axonactive.phonestore.service.dto.CustomerDto;
import com.axonactive.phonestore.service.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> getAll() {
        return customerMapper.toDtos(customerRepository.findAll());
    }

    @Override
    public CustomerDto findById(Integer id) throws EntityNotFoundException {
        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(BusinessLogicException::CustomerNotFound));
    }

    @Override
    public CustomerDto save(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setAddress(customerRequest.getAddress());
        customer.setFullName(customerRequest.getFullName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto update(Integer id, CustomerRequest customerRequest) throws EntityNotFoundException {
        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(BusinessLogicException::CustomerNotFound);
        updatedCustomer.setFullName(customerRequest.getFullName());
        updatedCustomer.setAddress(customerRequest.getAddress());
        updatedCustomer.setPhoneNumber(customerRequest.getPhoneNumber());
        return customerMapper.toDto(customerRepository.save(updatedCustomer));
    }

    @Override
    public List<CustomerDto> findByPhoneNumberContaining(String phoneNumber) {
        return customerMapper.toDtos(customerRepository.findByPhoneNumberContaining(phoneNumber));
    }

    @Override
    public List<CustomerDto> findByFullNameContaining(String name) {
        return customerMapper.toDtos(customerRepository.findByFullNameContaining(name));
    }
}
