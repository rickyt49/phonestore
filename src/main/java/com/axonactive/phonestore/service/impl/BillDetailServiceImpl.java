package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillDetailRepository;
import com.axonactive.phonestore.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    BillDetailRepository billDetailRepository;

    @Override
    public List<BillDetail> getAll() {
        return billDetailRepository.findAll();
    }

    @Override
    public Optional<BillDetail> findById(Integer id) {
        return billDetailRepository.findById(id);
    }

    @Override
    public BillDetail save(BillDetail billDetail) {
        return billDetailRepository.save(billDetail);
    }

    @Override
    public void delete(Integer id) {
        billDetailRepository.deleteById(id);
    }

    @Override
    public BillDetail update(Integer id, BillDetail billDetailDetails) throws ResourceNotFoundException {
        BillDetail updatedBillDetail = billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found: " + id));
        updatedBillDetail.setPhysicalPhone(billDetailDetails.getPhysicalPhone());
        updatedBillDetail.setSellPrice(billDetailDetails.getSellPrice());
        updatedBillDetail.setDiscountAmount(billDetailDetails.getDiscountAmount());
        updatedBillDetail.setBill(billDetailDetails.getBill());

        return billDetailRepository.save(updatedBillDetail);
    }

    @Override
    public List<BillDetail> findByBillId(Integer id) {
        return billDetailRepository.findByBillId(id);
    }



}
