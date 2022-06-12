package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    List<Owner> findByPhoneNumberContaining(String phoneNumber);

    List<Owner> findByFullNameContaining(String name);
}
