package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Integer> {
}
