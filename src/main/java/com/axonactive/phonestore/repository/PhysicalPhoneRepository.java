package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.PhysicalPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhysicalPhoneRepository extends JpaRepository<PhysicalPhone, Integer> {
    Optional<PhysicalPhone> findByImei(String imei);

    @Query("FROM PhysicalPhone p WHERE p.phoneStatus = 'AVAILABLE'")
    List<PhysicalPhone> findAllAvailablePhone();

    @Query("FROM PhysicalPhone p WHERE p.phoneStatus = 'AVAILABLE' AND p.store.id = ?1")
    List<PhysicalPhone> findAllAvailablePhoneByStore(Integer storeId);
}
