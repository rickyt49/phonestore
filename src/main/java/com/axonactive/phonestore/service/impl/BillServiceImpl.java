package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.BillRepository;
import com.axonactive.phonestore.repository.CustomerRepository;
import com.axonactive.phonestore.repository.EmployeeRepository;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<BillDto> getAll() {
        List<BillDto> billDtos = (billMapper.toDtos(billRepository.findAll()));
        billDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return billDtos;
    }

    @Override
    public BillDto findById(Integer id) throws ResourceNotFoundException {
        BillDto resultBill = billMapper.toDto(billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id)));
        resultBill.setBillDetailDtos(billDetailService.findByBillId(id));
        return resultBill;
    }

    @Override
    public BillDto save(BillRequest billRequest) throws ResourceNotFoundException {
        Bill bill = new Bill();
        bill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        bill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        bill.setTotalSellPrice(0);
        return billMapper.toDto(billRepository.save(bill));
    }

    @Override
    public void delete(Integer id) {
        billRepository.deleteById(id);
    }

    @Override
    public BillDto update(Integer id, BillRequest billRequest) throws ResourceNotFoundException {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id));
        bill.setCustomer(customerRepository.findById(billRequest.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found! " + billRequest.getCustomerId())));
        bill.setEmployee(employeeRepository.findById(billRequest.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found! " + billRequest.getEmployeeId())));
        Bill updatedBill = billRepository.save(bill);
        BillDto updatedBillDto = billMapper.toDto(updatedBill);
        updatedBillDto.setBillDetailDtos(billDetailService.findByBillId(updatedBillDto.getId()));
        return updatedBillDto;
    }

    @Override
    public List<BillDto> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBetween(id, startDate, endDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findByEmployeeId(Integer id) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findByEmployeeId(id));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateBefore(id, endDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findByEmployeeIdAndSaleDateAfter(id, startDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findAllBillByStore(Integer storeId) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findAllBillByStore(storeId));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findAllBillByStoreAndSaleDateAfter(Integer storeId, LocalDate startDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateAfter(storeId, startDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findAllBillByStoreAndSaleDateBefore(Integer storeId, LocalDate endDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBefore(storeId, endDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
    }

    @Override
    public List<BillDto> findAllBillByStoreAndSaleDateBetween(Integer storeId, LocalDate startDate, LocalDate endDate) {
        List<BillDto> resultBillDtos = billMapper.toDtos(billRepository.findAllBillByStoreAndSaleDateBetween(storeId, startDate, endDate));
        resultBillDtos.forEach(b -> b.setBillDetailDtos(billDetailService.findByBillId(b.getId())));
        return resultBillDtos;
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


}
