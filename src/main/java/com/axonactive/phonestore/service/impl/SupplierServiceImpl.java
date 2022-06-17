package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.SupplierRequest;
import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.SupplierRepository;
import com.axonactive.phonestore.service.SupplierService;
import com.axonactive.phonestore.service.dto.SupplierDto;
import com.axonactive.phonestore.service.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> getAll() {
        return supplierMapper.toDtos(supplierRepository.findAll());
    }

    @Override
    public SupplierDto findById(Integer id) throws ResourceNotFoundException {
        return supplierMapper.toDto(supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id)));
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
        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierDto update(Integer id, SupplierRequest supplierRequest) throws ResourceNotFoundException {
        Supplier updatedSupplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
        updatedSupplier.setFullName(supplierRequest.getFullName());
        updatedSupplier.setAddress(supplierRequest.getAddress());
        updatedSupplier.setPhoneNumber(supplierRequest.getPhoneNumber());
        return supplierMapper.toDto(supplierRepository.save(updatedSupplier));
    }

    @Override
    public List<SupplierDto> findByPhoneNumberContaining(String phoneNumber) {
        return supplierMapper.toDtos(supplierRepository.findByPhoneNumberContaining(phoneNumber));
    }

    @Override
    public List<SupplierDto> findByFullNameContaining(String name) {
        return supplierMapper.toDtos(supplierRepository.findByFullNameContaining(name));
    }
}
