package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.BillDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface BillService {
    List<BillDto> getAll();

    BillDto findById(Integer id) ;

    BillDto save(BillRequest bill) ;

    void delete(Integer id) ;

    BillDto update(Integer id, BillRequest billRequest) ;

    List<BillDto> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate);

    List<BillDto> findByEmployeeId(Integer id);

    List<BillDto> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate);

    List<BillDto> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate);

    List<BillDto> findAllBillByStore(Integer storeId);

    List<BillDto> findAllBillByStoreAndSaleDateAfter(Integer storeId, LocalDate startDate);

    List<BillDto> findAllBillByStoreAndSaleDateBefore(Integer storeId, LocalDate endDate);

    List<BillDto> findAllBillByStoreAndSaleDateBetween(Integer storeId, LocalDate startDate, LocalDate endDate);


    Integer getTotalGrossProfit(Integer storeId);

    Integer getTotalGrossProfitAfter(Integer storeId, LocalDate startDate);

    Integer getTotalGrossProfitBefore(Integer storeId,  LocalDate endDate);

    Integer getTotalGrossProfitBetween(Integer storeId, LocalDate startDate,LocalDate endDate);


}
