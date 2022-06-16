package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillDetailRepository;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    BillDetailRepository billDetailRepository;
    @Autowired
    BillRepository billRepository;

    @Override
    public List<BillDetail> getAll() {
        return billDetailRepository.findAll();
    }

    @Override
    public Optional<BillDetail> findById(Integer id) {
        return billDetailRepository.findById(id);
    }

    @Override
    public BillDetail save(BillDetail billDetail) throws ResourceNotFoundException {
        BillDetail createdBillDetail = billDetailRepository.save(billDetail);
        updateTotalPrice(billDetail);
        return createdBillDetail;
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        BillDetail billDetail = billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found " + id));
        billDetailRepository.deleteById(id);
        updateTotalPrice(billDetail);
    }

    @Override
    public BillDetail update(Integer id, BillDetail billDetailDetails) throws ResourceNotFoundException {
        BillDetail updatedBillDetail = billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found: " + id));
        updatedBillDetail.setPhysicalPhone(billDetailDetails.getPhysicalPhone());
        updatedBillDetail.setSellPrice(billDetailDetails.getSellPrice());
        updatedBillDetail.setDiscountAmount(billDetailDetails.getDiscountAmount());
        updatedBillDetail.setFinalSellPrice(billDetailDetails.getFinalSellPrice());
        updateTotalPrice(updatedBillDetail);
        return billDetailRepository.save(updatedBillDetail);
    }

    @Override
    public List<BillDetail> findByBillId(Integer id) {
        return billDetailRepository.findByBillId(id);
    }



    public void updateTotalPrice(BillDetail billDetail) throws ResourceNotFoundException {
        Bill bill = billRepository.findById(billDetail.getBill().getId()).orElseThrow(() -> new ResourceNotFoundException("Bill not found" + billDetail.getBill().getId()));
        bill.setTotalSellPrice(billDetailRepository.sumSellPriceByBillId(billDetail.getBill().getId()));
        billRepository.save(bill);
    }

}
