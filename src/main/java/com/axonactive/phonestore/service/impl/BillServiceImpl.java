package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.entity.enumerate.PhoneStatus;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.repository.CustomerRepository;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.mapper.BillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service


public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PhysicalPhoneRepository physicalPhoneRepository;

    @Override
    public List<BillDto> getAll() {
        log.info("Searching for all bill");
        return (billMapper.toDtos(billRepository.findAll()));
    }

    @Override
    public BillDto findById(Integer id)  {
        log.info("Searching for bill has id {}", id);
        return billMapper.toDto(billRepository.findById(id).orElseThrow(BusinessLogicException::BillNotFound));
    }

    @Override
    public BillDto save(BillRequest billRequest)  {
        Bill bill = new Bill();
        List<BillDetail> billDetailList = new ArrayList<>();

        log.info("Searching for customer has id {}", billRequest.getCustomerId());
        bill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(BusinessLogicException::CustomerNotFound));

        log.info("Searching for employee has id {}", billRequest.getEmployeeId());
        bill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(BusinessLogicException::EmployeeNotFound));
        for (BillDetailUpdateRequest billDetailUpdateRequest : billRequest.getBillDetailsRequest()) {
            BillDetail billDetail = new BillDetail();

            log.info("Searching for phone has imei {}", billDetailUpdateRequest.getImei());
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
            foundPhone.setPhoneStatus(PhoneStatus.SOLD);
            billDetail.setPhysicalPhone(foundPhone);
            billDetail.setSellPrice(billDetailUpdateRequest.getSellPrice());
            billDetail.setDiscountAmount(billDetailUpdateRequest.getDiscountAmount());
            billDetail.setFinalSellPrice(billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount());
            billDetail.setBill(bill);
            billDetailList.add(billDetail);

        }
        bill.setBillDetails(billDetailList);
        bill.setTotalSellPrice();
        return billMapper.toDto(billRepository.saveAndFlush(bill));

    }

    @Override
    public void delete(Integer id)  {
        log.info("Searching for bill has id {}", id);
        Bill foundBill = billRepository.findById(id).orElseThrow(BusinessLogicException::BillNotFound);
        for (BillDetail billDetail :
                foundBill.getBillDetails()) {
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetail.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
            foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
        }
        billRepository.deleteById(id);
    }

    @Override
    public BillDto update(Integer id, BillRequest billRequest)  {
        log.info("Searching for bill has id {}", id);
        Bill updatedBill = billRepository.findById(id).orElseThrow(BusinessLogicException::BillNotFound);
        List<BillDetail> billDetailList = new ArrayList<>();
        for (BillDetail bD : updatedBill.getBillDetails()) {
            if (!(billRequest.getBillDetailsRequest().stream().map(BillDetailUpdateRequest::getImei).collect(Collectors.toList()).contains(bD.getPhysicalPhone().getImei()))) {
                log.info("Searching for phone has imei {}", bD.getPhysicalPhone().getImei());
                PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(bD.getPhysicalPhone().getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
                foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
            }
        }
        log.info("Searching for customer has id {}", billRequest.getCustomerId());
        updatedBill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(BusinessLogicException::CustomerNotFound));

        log.info("Searching for employee has id {}", billRequest.getEmployeeId());
        updatedBill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(BusinessLogicException::EmployeeNotFound));
        for (BillDetailUpdateRequest billDetailUpdateRequest : billRequest.getBillDetailsRequest()) {
            BillDetail billDetail = new BillDetail();

            log.info("Searching for phone has imei {}", billDetailUpdateRequest.getImei());
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(BusinessLogicException::PhoneNotFound);
            foundPhone.setPhoneStatus(PhoneStatus.SOLD);

            billDetail.setPhysicalPhone(foundPhone);
            billDetail.setSellPrice(billDetailUpdateRequest.getSellPrice());
            billDetail.setDiscountAmount(billDetailUpdateRequest.getDiscountAmount());
            billDetail.setFinalSellPrice(billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount());
            billDetail.setBill(updatedBill);
            billDetailList.add(billDetail);
        }
        updatedBill.getBillDetails().clear();
        updatedBill.getBillDetails().addAll(billDetailList);
        updatedBill.setTotalSellPrice();
        return billMapper.toDto(billRepository.saveAndFlush(updatedBill));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate) {
        log.info("Searching for all bill by employee has id {} between {} and {}", id, startDate, endDate);
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBetween(id, startDate, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeId(Integer id) {
        log.info("Searching for all bill by employee has id {}", id);
        return billMapper.toDtos(billRepository.findByEmployeeId(id));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate) {
        log.info("Searching for all bill by employee has id {} before {}", id, endDate);
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBefore(id, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate) {
        log.info("Searching for all bill by employee has id {} after {}", id, startDate);
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateAfter(id, startDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStore(Integer storeId) {
        log.info("Searching for all bill in store has id {}", storeId);
        return billMapper.toDtos(billRepository.findAllBillByStore(storeId));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateAfter(Integer storeId, LocalDate startDate) {
        log.info("Searching for all bill in store has id {} after {}", storeId, startDate);

        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateAfter(storeId, startDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateBefore(Integer storeId, LocalDate endDate) {
        log.info("Searching for all bill in store has id {} before {}", storeId, endDate);

        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBefore(storeId, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateBetween(Integer storeId, LocalDate startDate, LocalDate endDate) {
        log.info("Searching for all bill in store has id {} between {} and {}", storeId, startDate, endDate);

        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBetween(storeId, startDate, endDate));
    }

    @Override
    public Integer getTotalGrossProfit(Integer storeId) {
        log.info("Getting total gross profit of store has id {}", storeId);
        return billRepository.getTotalGrossProfit(storeId);
    }

    @Override
    public Integer getTotalGrossProfitAfter(Integer storeId, LocalDate startDate) {
        log.info("Getting total gross profit of store has id {} after {}", storeId, startDate);

        return billRepository.getTotalGrossProfitAfter(storeId, startDate);
    }

    @Override
    public Integer getTotalGrossProfitBefore(Integer storeId, LocalDate endDate) {
        log.info("Getting total gross profit of store has id {} before {}", storeId, endDate);

        return billRepository.getTotalGrossProfitBefore(storeId, endDate);
    }

    @Override
    public Integer getTotalGrossProfitBetween(Integer storeId, LocalDate startDate, LocalDate endDate) {
        log.info("Getting total gross profit of store has id {} between {} and {}", storeId, startDate, endDate);
        return billRepository.getTotalGrossProfitBetween(storeId, startDate, endDate);
    }


}