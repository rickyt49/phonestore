package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Specification;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SpecificationService {
    List<Specification> getAll();

    Optional<Specification> findById(Integer id);

    Specification save(Specification specification);

    void delete(Integer id);

    Specification update(Integer id, Specification specificationDetails) throws ResourceNotFoundException;
}
