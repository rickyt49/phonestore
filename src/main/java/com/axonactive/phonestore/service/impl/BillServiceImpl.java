package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

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
        updatedBill.setTotalSellPrice(billDetails.getTotalSellPrice());
        updatedBill.setCustomer(billDetails.getCustomer());
        updatedBill.setEmployee(billDetails.getEmployee());
        return billRepository.save(updatedBill);
    }
}
