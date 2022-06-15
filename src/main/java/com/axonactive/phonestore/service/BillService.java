package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillService {
    List<Bill> getAll();

    Optional<Bill> findById(Integer id);

    Bill save(Bill bill);

    void delete(Integer id);

    Bill update(Integer id, Bill billDetails) throws ResourceNotFoundException;

    List<Bill> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate);

    List<Bill> findByEmployeeId(Integer id);

    List<Bill> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate);

    List<Bill> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate);
}
