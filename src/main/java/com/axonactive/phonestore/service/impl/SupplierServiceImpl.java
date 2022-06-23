package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.SupplierRequest;
import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.SupplierRepository;
import com.axonactive.phonestore.service.SupplierService;
import com.axonactive.phonestore.service.dto.SupplierDto;
import com.axonactive.phonestore.service.mapper.SupplierMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.BufferUnderflowException;
import java.util.List;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> getAll() {
        log.info("Searching for all supplier");
        return supplierMapper.toDtos(supplierRepository.findAll());
    }

    @Override
    public SupplierDto findById(Integer id) {
        log.info("Searching for supplier has id {}", id);
        return supplierMapper.toDto(supplierRepository.findById(id).orElseThrow(BusinessLogicException::SupplierNotFound));
    }

    @Override
    public SupplierDto save(SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setFullName(supplierRequest.getFullName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setPhoneNumber(supplierRequest.getPhoneNumber());
        return supplierMapper.toDto(supplierRepository.save(supplier));
    }

    @Override
    public void delete(Integer id) {
        log.info("Searching for supplier has id {}", id);
        supplierRepository.findById(id).orElseThrow(BusinessLogicException::SupplierNotFound);
        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierDto update(Integer id, SupplierRequest supplierRequest) {
        log.info("Searching for supplier has id {}", id);

        Supplier updatedSupplier = supplierRepository.findById(id).orElseThrow(BusinessLogicException::SupplierNotFound);
        updatedSupplier.setFullName(supplierRequest.getFullName());
        updatedSupplier.setAddress(supplierRequest.getAddress());
        updatedSupplier.setPhoneNumber(supplierRequest.getPhoneNumber());
        return supplierMapper.toDto(supplierRepository.save(updatedSupplier));
    }

    @Override
    public List<SupplierDto> findByPhoneNumberContaining(String phoneNumber) {
        log.info("Searching for supplier with phone number containing {}", phoneNumber);
        if (!(phoneNumber.matches("[0-9]+"))) {
            throw BusinessLogicException.PhoneNumberBadRequest();
        } else
            return supplierMapper.toDtos(supplierRepository.findByPhoneNumberContaining(phoneNumber));
    }

    @Override
    public List<SupplierDto> findByFullNameContaining(String name) {
        log.info("Searching for supplier with name containing {}", name);
        return supplierMapper.toDtos(supplierRepository.findByFullNameContaining(name));
    }
}
