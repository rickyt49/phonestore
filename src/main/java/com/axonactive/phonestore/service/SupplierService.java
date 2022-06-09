package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAll();

    Optional<Supplier> findById(Integer id);

    Supplier save(Supplier owner);

    void delete(Integer id);

    Supplier update(Integer id, Supplier supplierDetails) throws ResourceNotFoundException;
}
