package com.axonactive.phonestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetail extends JpaRepository<BillDetail, Integer> {
}
