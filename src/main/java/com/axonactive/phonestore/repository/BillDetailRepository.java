package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
    List<BillDetail> findByBillId(Integer id);

    @Query("SELECT sum(b.finalSellPrice) FROM BillDetail b WHERE b.bill.id = ?1")
    Integer sumSellPriceByBillId(Integer billId);
}
