package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByPhoneNumberContaining(String phoneNumber);

    List<Customer> findByFullNameContaining(String name);
}
