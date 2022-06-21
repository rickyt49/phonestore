package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.entity.PhoneStatus;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.BillDetailRepository;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
    public BillDetailDto findById(Integer id) throws EntityNotFoundException {
        return billDetailMapper.toDto(billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound));
    }

    @Override
    public BillDetailDto save(BillDetailRequest billDetailRequest) throws EntityNotFoundException {
        BillDetail billDetail = new BillDetail();
        billDetail.setBill(billRepository.findById(billDetailRequest.getBillId()).orElseThrow(BusinessLogicException::BillDetailNotFound));
        PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailRequest.getImei()).orElseThrow(BusinessLogicException::BillDetailNotFound);
        foundPhone.setPhoneStatus(PhoneStatus.SOLD);
        billDetail.setPhysicalPhone(foundPhone);
        billDetail.setSellPrice(billDetailRequest.getSellPrice());
        billDetail.setDiscountAmount(billDetailRequest.getDiscountAmount());
        billDetail.setFinalSellPrice(billDetailRequest.getSellPrice() - billDetailRequest.getDiscountAmount());
        BillDetail createdBillDetail = billDetailRepository.save(billDetail);
        updateTotalPrice(createdBillDetail);
        return billDetailMapper.toDto(createdBillDetail);
    }

    @Override
    public void delete(Integer id) throws EntityNotFoundException {
        BillDetail billDetail = billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound);
        PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetail.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
        foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
        billDetail.setPhysicalPhone(foundPhone);
        billDetailRepository.deleteById(id);
        updateTotalPrice(billDetail);
    }

    @Override
    public BillDetailDto update(Integer id, BillDetailUpdateRequest billDetailUpdateRequest) throws EntityNotFoundException {
        BillDetail updatedBillDetail = billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound);
        PhysicalPhone oldPhone = physicalPhoneRepository.findByImei(updatedBillDetail.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
        oldPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
        PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(BusinessLogicException::BillDetailNotFound);
        foundPhone.setPhoneStatus(PhoneStatus.SOLD);
        updatedBillDetail.setPhysicalPhone(foundPhone);
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


    public void updateTotalPrice(BillDetail billDetail) throws EntityNotFoundException {
        Bill bill = billRepository.findById(billDetail.getBill().getId()).orElseThrow(BusinessLogicException::BillNotFound);
        bill.setTotalSellPrice(billDetailRepository.sumSellPriceByBillId(billDetail.getBill().getId()));
        billRepository.save(bill);
    }

}
