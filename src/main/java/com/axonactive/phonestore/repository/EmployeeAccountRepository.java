package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Integer> {

    Optional<EmployeeAccount> findByUsername(String username);
}
