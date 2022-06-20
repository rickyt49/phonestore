package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.entity.PhoneStatus;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.repository.CustomerRepository;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service


public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    BillDetailService billDetailService;

    @Autowired
    BillMapper billMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    PhysicalPhoneRepository physicalPhoneRepository;

    @Override
    public List<BillDto> getAll() {
        return (billMapper.toDtos(billRepository.findAll()));
    }

    @Override
    public BillDto findById(Integer id) throws EntityNotFoundException {
        return billMapper.toDto(billRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bill not found: " + id)));
    }

    @Override
    public BillDto save(BillRequest billRequest) throws EntityNotFoundException, BusinessLogicException {
        Bill bill = new Bill();
        List<BillDetail> billDetailList = new ArrayList<>();
        bill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        bill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        for (BillDetailUpdateRequest billDetailUpdateRequest : billRequest.getBillDetailsRequest()) {
            BillDetail billDetail = new BillDetail();
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(() -> new EntityNotFoundException("Phone not found: " + billDetailUpdateRequest.getImei()));
            foundPhone.setPhoneStatus(PhoneStatus.SOLD);
            billDetail.setPhysicalPhone(foundPhone);
            billDetail.setSellPrice(billDetailUpdateRequest.getSellPrice());
            billDetail.setDiscountAmount(billDetailUpdateRequest.getDiscountAmount());
            billDetail.setFinalSellPrice(billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount());
            billDetail.setBill(bill);
            if (isValidBillDetailCreation(billDetail)) {
                billDetailList.add(billDetail);
            } else throw new BusinessLogicException("Phone is not in same store as employee");
        }
        bill.setBillDetails(billDetailList);
        bill.setTotalSellPrice();
        return billMapper.toDto(billRepository.saveAndFlush(bill));

    }

    @Override
    public void delete(Integer id) throws EntityNotFoundException {
        Bill foundBill = billRepository.findById(id).get();
        for (BillDetail billDetail :
                foundBill.getBillDetails()) {
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetail.getPhysicalPhone().getImei()).orElseThrow(() -> new EntityNotFoundException("Phone not found: " + billDetail.getPhysicalPhone().getImei()));
            foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
        }
        billRepository.deleteById(id);
    }

    @Override
    public BillDto update(Integer id, BillRequest billRequest) throws EntityNotFoundException, BusinessLogicException {
        Bill updatedBill = billRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bill not found: " + id));
        List<BillDetail> billDetailList = new ArrayList<>();
        for (BillDetail bD : updatedBill.getBillDetails()) {
            if (!(billRequest.getBillDetailsRequest().stream().map(BillDetailUpdateRequest::getImei).collect(Collectors.toList()).contains(bD.getPhysicalPhone().getImei()))) {
                PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(bD.getPhysicalPhone().getImei()).orElseThrow(() -> new EntityNotFoundException("Phone not found: " + bD.getPhysicalPhone().getImei()));
                foundPhone.setPhoneStatus(PhoneStatus.AVAILABLE);
            }
        }
        updatedBill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        updatedBill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        for (BillDetailUpdateRequest billDetailUpdateRequest : billRequest.getBillDetailsRequest()) {
            BillDetail billDetail = new BillDetail();
            PhysicalPhone foundPhone = physicalPhoneRepository.findByImei(billDetailUpdateRequest.getImei()).orElseThrow(() -> new EntityNotFoundException("Phone not found: " + billDetailUpdateRequest.getImei()));
            foundPhone.setPhoneStatus(PhoneStatus.SOLD);
            billDetail.setPhysicalPhone(foundPhone);
            billDetail.setSellPrice(billDetailUpdateRequest.getSellPrice());
            billDetail.setDiscountAmount(billDetailUpdateRequest.getDiscountAmount());
            billDetail.setFinalSellPrice(billDetailUpdateRequest.getSellPrice() - billDetailUpdateRequest.getDiscountAmount());
            billDetail.setBill(updatedBill);
            if (isValidBillDetailCreation(billDetail)) {
                billDetailList.add(billDetail);
            } else throw new BusinessLogicException("Phone is not in same store as employee");
        }
        updatedBill.getBillDetails().clear();
        updatedBill.getBillDetails().addAll(billDetailList);
        updatedBill.setTotalSellPrice();
        return billMapper.toDto(billRepository.saveAndFlush(updatedBill));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate) {
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBetween(id, startDate, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeId(Integer id) {
        return billMapper.toDtos(billRepository.findByEmployeeId(id));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate) {
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBefore(id, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate) {
        return billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateAfter(id, startDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStore(Integer storeId) {
        return billMapper.toDtos(billRepository.findAllBillByStore(storeId));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateAfter(Integer storeId, LocalDate startDate) {
        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateAfter(storeId, startDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateBefore(Integer storeId, LocalDate endDate) {
        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBefore(storeId, endDate));
    }

    @Override
    @Transactional
    public List<BillDto> findAllBillByStoreAndSaleDateBetween(Integer storeId, LocalDate startDate, LocalDate endDate) {
        return billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBetween(storeId, startDate, endDate));
    }

    @Override
    public Integer getTotalGrossProfit(Integer storeId) {
        return billRepository.getTotalGrossProfit(storeId);
    }

    @Override
    public Integer getTotalGrossProfitAfter(Integer storeId, LocalDate startDate) {
        return billRepository.getTotalGrossProfitAfter(storeId, startDate);
    }

    @Override
    public Integer getTotalGrossProfitBefore(Integer storeId, LocalDate endDate) {
        return billRepository.getTotalGrossProfitBefore(storeId, endDate);
    }

    @Override
    public Integer getTotalGrossProfitBetween(Integer storeId, LocalDate startDate, LocalDate endDate) {
        return billRepository.getTotalGrossProfitBetween(storeId, startDate, endDate);
    }

    public boolean isValidBillDetailCreation(BillDetail billDetail) {
        return Objects.equals(billDetail.getBill().getEmployee().getStore().getId(), billDetail.getPhysicalPhone().getStore().getId());
    }
}
