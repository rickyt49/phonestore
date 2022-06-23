package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.entity.enumerate.PhoneStatus;
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
    private BillDetailRepository billDetailRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private PhysicalPhoneRepository physicalPhoneRepository;

    @Override
    public List<BillDetailDto> getAll() {
        log.info("Searching for all billDetail has id");
        return billDetailMapper.toDtos(billDetailRepository.findAll());
    }

    @Override
    public BillDetailDto findById(Integer id)  {
        log.info("Searching for billDetail has id {} ", id);
        return billDetailMapper.toDto(billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound));
    }

    @Override
    public BillDetailDto save(BillDetailRequest billDetailRequest)  {
        BillDetail billDetail = new BillDetail();

        log.info("Searching for bill has id {} ", billDetailRequest.getBillId());
        billDetail.setBill(billRepository.findById(billDetailRequest.getBillId()).orElseThrow(BusinessLogicException::BillDetailNotFound));

        log.info("Searching for phone has imei {} ", billDetail.getPhysicalPhone().getImei());
        PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailRequest.getImei()).orElseThrow(BusinessLogicException::BillDetailNotFound);
        foundPhone.setPhoneStatus(PhoneStatus.SOLD);

        billDetail.setPhysicalPhone(foundPhone);
        billDetail.setSellPrice(billDetailRequest.getSellPrice());
        billDetail.setDiscountAmount(billDetailRequest.getDiscountAmount());
        billDetail.setFinalSellPrice(billDetailRequest.getSellPrice() - billDetailRequest.getDiscountAmount());
        BillDetail createdBillDetail = billDetailRepository.save(billDetail);

        log.info("Updating total price of bill has id {}", createdBillDetail.getBill().getId());
        updateTotalPrice(createdBillDetail);
        return billDetailMapper.toDto(createdBillDetail);
    }

    @Override
    public void delete(Integer id)  {
        log.info("Searching for billDetail has id {} ", id);
        BillDetail billDetail = billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound);

        log.info("Searching for phone has imei {} ", billDetail.getPhysicalPhone().getImei());
        PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetail.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
        foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
        billDetail.setPhysicalPhone(foundPhone);
        billDetailRepository.deleteById(id);
        updateTotalPrice(billDetail);
    }

    @Override
    public BillDetailDto update(Integer id, BillDetailUpdateRequest billDetailUpdateRequest)  {
        BillDetail updatedBillDetail = billDetailRepository.findById(id).orElseThrow(BusinessLogicException::BillDetailNotFound);
        log.info("Searching for phone has imei {} ", updatedBillDetail.getPhysicalPhone().getImei());
        PhysicalPhone oldPhone = physicalPhoneRepository.findByImei(updatedBillDetail.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
        oldPhone.setPhoneStatus(PhoneStatus.AVAILABLE);

        log.info("Searching for phone has imei {} ", billDetailUpdateRequest.getImei());
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
        log.info("Searching for all bill detail of bill has id {} ", id);
        return billDetailMapper.toDtos(billDetailRepository.findByBillId(id));
    }


    public void updateTotalPrice(BillDetail billDetail)  {
        Bill bill = billRepository.findById(billDetail.getBill().getId()).orElseThrow(BusinessLogicException::BillNotFound);
        bill.setTotalSellPrice(billDetailRepository.sumSellPriceByBillId(billDetail.getBill().getId()));
        billRepository.save(bill);
    }

}
