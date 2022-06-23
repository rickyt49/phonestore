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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> getAll() {
        log.info("Searching for all customer");
        return customerMapper.toDtos(customerRepository.findAll());
    }

    @Override
    public CustomerDto findById(Integer id) {
        log.info("Searching for customer has id {}", id);
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
        log.info("Searching for customer has id {}", id);
        customerRepository.findById(id).orElseThrow(BusinessLogicException::CustomerNotFound);
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto update(Integer id, CustomerRequest customerRequest) {
        log.info("Searching for customer has id {}", id);
        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(BusinessLogicException::CustomerNotFound);
        updatedCustomer.setFullName(customerRequest.getFullName());
        updatedCustomer.setAddress(customerRequest.getAddress());
        updatedCustomer.setPhoneNumber(customerRequest.getPhoneNumber());
        return customerMapper.toDto(customerRepository.save(updatedCustomer));
    }

    @Override
    public List<CustomerDto> findByPhoneNumberContaining(String phoneNumber) {
        log.info("Searching for customer with phone number containing {}", phoneNumber);
//        if (!(phoneNumber.matches("[0-9]+")))
//            throw BusinessLogicException.PhoneNumberBadRequest();
//        else {
        return customerMapper.toDtos(customerRepository.findByPhoneNumberContaining(phoneNumber));
//        }
    }

    @Override
    public List<CustomerDto> findByFullNameContaining(String name) {
        log.info("Searching for customer with name containing {}", name);
        return customerMapper.toDtos(customerRepository.findByFullNameContaining(name));
    }
}
