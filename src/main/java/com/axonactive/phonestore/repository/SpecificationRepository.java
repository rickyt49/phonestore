package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
    Optional<Specification> findByModelIgnoreCase(String model);
}
