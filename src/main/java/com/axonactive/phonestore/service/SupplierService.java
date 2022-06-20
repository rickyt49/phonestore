package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.SupplierRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAll();

    SupplierDto findById(Integer id) throws EntityNotFoundException;

    SupplierDto save(SupplierRequest supplierRequest);

    void delete(Integer id);

    SupplierDto update(Integer id, SupplierRequest supplierRequest) throws EntityNotFoundException;

    List<SupplierDto> findByPhoneNumberContaining(String phoneNumber);

    List<SupplierDto> findByFullNameContaining(String name);
}
