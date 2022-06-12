package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.SupplierRepository;
import com.axonactive.phonestore.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Integer id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier save(Supplier owner) {
        return supplierRepository.save(owner);
    }

    @Override
    public void delete(Integer id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public Supplier update(Integer id, Supplier supplierDetails) throws ResourceNotFoundException {
        Supplier updatedSupplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
        updatedSupplier.setFullName(supplierDetails.getFullName());
        updatedSupplier.setAddress(supplierDetails.getAddress());
        updatedSupplier.setPhoneNumber(supplierDetails.getPhoneNumber());
        return supplierRepository.save(updatedSupplier);
    }

    @Override
    public List<Supplier> findByPhoneNumberContaining(String phoneNumber) {
        return supplierRepository.findByPhoneNumberContaining(phoneNumber);
    }

    @Override
    public List<Supplier> findByFullNameContaining(String name) {
        return supplierRepository.findByFullNameContaining(name);
    }
}
