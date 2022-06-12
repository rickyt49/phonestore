package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByPhoneNumberContaining(String phoneNumber);

    List<Supplier> findByFullNameContaining(String name);
}
