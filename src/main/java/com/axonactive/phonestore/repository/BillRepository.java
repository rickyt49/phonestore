package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate);

    List<Bill> findByEmployeeId(Integer id);

    List<Bill> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate);

    List<Bill> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate);

    @Query("FROM Bill b WHERE b.employee.store.id = ?1")
    List<Bill> findAllBillByStore(Integer storeId);

    @Query("FROM Bill b WHERE b.employee.store.id = ?1 AND b.saleDate > ?2")
    List<Bill> findAllBillByStoreAndSaleDateAfter(Integer storeId, LocalDate startDate);

    @Query("FROM Bill b WHERE b.employee.store.id = ?1 AND b.saleDate < ?2")
    List<Bill> findAllBillByStoreAndSaleDateBefore(Integer storeId, LocalDate endDate);

    @Query("FROM Bill b WHERE b.employee.store.id = ?1 AND b.saleDate BETWEEN ?2 AND ?3")
    List<Bill> findAllBillByStoreAndSaleDateBetween(Integer storeId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(bd.finalSellPrice - p.importPrice) FROM BillDetail bd , PhysicalPhone p WHERE bd.physicalPhone.id = p.id AND p.store.id = ?1")
    Integer getTotalGrossProfit(Integer storeId);

    @Query("SELECT SUM(bd.finalSellPrice - p.importPrice) FROM BillDetail bd , PhysicalPhone p, Bill b WHERE b.id = bd.bill.id AND bd.physicalPhone.id = p.id AND p.store.id = ?1 and b.saleDate < ?2")
    Integer getTotalGrossProfitBefore(Integer storeId, LocalDate endDate);

    @Query("SELECT SUM(bd.finalSellPrice - p.importPrice) FROM BillDetail bd , PhysicalPhone p, Bill b WHERE b.id = bd.bill.id AND bd.physicalPhone.id = p.id AND p.store.id = ?1 and b.saleDate >?2")
    Integer getTotalGrossProfitAfter(Integer storeId, LocalDate startDate);

    @Query("SELECT SUM(bd.finalSellPrice - p.importPrice) FROM BillDetail bd , PhysicalPhone p, Bill b WHERE b.id = bd.bill.id AND bd.physicalPhone.id = p.id AND p.store.id = ?1 and b.saleDate BETWEEN ?2 and ?3")
    Integer getTotalGrossProfitBetween(Integer storeId, LocalDate startDate, LocalDate endDate);
}
