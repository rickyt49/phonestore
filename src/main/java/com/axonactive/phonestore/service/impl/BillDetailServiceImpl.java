package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillDetailRepository;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    BillDetailRepository billDetailRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    BillDetailMapper billDetailMapper;

    @Autowired
    PhysicalPhoneRepository physicalPhoneRepository;

    @Override
    public List<BillDetailDto> getAll() {
        return billDetailMapper.toDtos(billDetailRepository.findAll());
    }

    @Override
    public BillDetailDto findById(Integer id) throws ResourceNotFoundException {
        return billDetailMapper.toDto(billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found " + id)));
    }

    @Override
    public BillDetailDto save(BillDetailRequest billDetailRequest) throws ResourceNotFoundException {
        BillDetail billDetail = new BillDetail();
        billDetail.setBill(billRepository.findById(billDetailRequest.getBillId()).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + billDetailRequest.getBillId())));
        billDetail.setPhysicalPhone(physicalPhoneRepository.findByImei(billDetailRequest.getImei()).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + billDetailRequest.getImei())));
        billDetail.setSellPrice(billDetailRequest.getSellPrice());
        billDetail.setDiscountAmount(billDetailRequest.getDiscountAmount());
        billDetail.setFinalSellPrice(billDetailRequest.getSellPrice() - billDetailRequest.getDiscountAmount());
        BillDetail createdBillDetail = billDetailRepository.save(billDetail);
        updateTotalPrice(createdBillDetail);
        return billDetailMapper.toDto(createdBillDetail);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        BillDetail billDetail = billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found " + id));
        billDetailRepository.deleteById(id);
        updateTotalPrice(billDetail);
    }

    @Override
    public BillDetailDto update(Integer id, BillDetailUpdateRequest billDetailUpdateRequest) throws ResourceNotFoundException {
        BillDetail updatedBillDetail = billDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found: " + id));
        updatedBillDetail.setPhysicalPhone(physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + billDetailUpdateRequest.getImei())));
        updatedBillDetail.setSellPrice(billDetailUpdateRequest.getSellPrice());
        updatedBillDetail.setDiscountAmount(billDetailUpdateRequest.getDiscountAmount());
        if (updatedBillDetail.getFinalSellPrice() != billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount()) {
            updatedBillDetail.setFinalSellPrice(billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount());
            updateTotalPrice(updatedBillDetail);
        }

        return billDetailMapper.toDto(billDetailRepository.save(updatedBillDetail));
    }

    @Override
    public List<BillDetailDto> findByBillId(Integer id) {
        return billDetailMapper.toDtos(billDetailRepository.findByBillId(id));
    }


    public void updateTotalPrice(BillDetail billDetail) throws ResourceNotFoundException {
        Bill bill = billRepository.findById(billDetail.getBill().getId()).orElseThrow(() -> new ResourceNotFoundException("Bill not found" + billDetail.getBill().getId()));
        bill.setTotalSellPrice(billDetailRepository.sumSellPriceByBillId(billDetail.getBill().getId()));
        billRepository.save(bill);
    }

}
