package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    BillDetailService billDetailService;

    @Override
    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> findById(Integer id) {
        return billRepository.findById(id);
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public void delete(Integer id) {
        billRepository.deleteById(id);
    }

    @Override
    public Bill update(Integer id, Bill billDetails) throws ResourceNotFoundException {
        Bill updatedBill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id));
        updatedBill.setSaleDate(billDetails.getSaleDate());
        updatedBill.setCustomer(billDetails.getCustomer());
        updatedBill.setEmployee(billDetails.getEmployee());
        return billRepository.save(updatedBill);
    }

    @Override
    public List<Bill> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate) {
        return billRepository.findByEmployeeIdAndSaleDateBetween(id, startDate, endDate);
    }

    @Override
    public List<Bill> findByEmployeeId(Integer id) {
        return billRepository.findByEmployeeId(id);
    }

    @Override
    public List<Bill> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate) {
        return billRepository.findByEmployeeIdAndSaleDateBefore(id, endDate);
    }

    @Override
    public List<Bill> findByEmployeeIdAndSaleDateAfter(Integer id,LocalDate startDate) {
        return billRepository.findByEmployeeIdAndSaleDateAfter(id,startDate);
    }



}
