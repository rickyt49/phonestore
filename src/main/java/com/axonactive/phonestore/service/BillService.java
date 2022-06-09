package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BillService {
    List<Bill> getAll();

    Optional<Bill> findById(Integer id);

    Bill save(Bill bill);

    void delete(Integer id);

    Bill update(Integer id, Bill billDetails) throws ResourceNotFoundException;

}
