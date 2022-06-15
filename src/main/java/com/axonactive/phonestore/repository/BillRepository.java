package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findByEmployeeIdAndSaleDateBetween(Integer id, LocalDate startDate, LocalDate endDate);

    List<Bill> findByEmployeeId(Integer id);

    List<Bill> findByEmployeeIdAndSaleDateBefore(Integer id, LocalDate endDate);

    List<Bill> findByEmployeeIdAndSaleDateAfter(Integer id, LocalDate startDate);
}
