package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("FROM Employee e WHERE e.employeeStatus = 'ACTIVE'")
    List<Employee> getAllActiveEmployee();
}
