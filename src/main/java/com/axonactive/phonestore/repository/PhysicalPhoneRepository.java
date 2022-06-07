package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.PhysicalPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalPhoneRepository extends JpaRepository<PhysicalPhone, Integer> {
}
