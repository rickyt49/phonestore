package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> getAll();

    Optional<Owner> findById(Integer id);

    Owner save(Owner owner);

    void delete(Integer id);

    Owner update(Integer id, Owner ownerDetails) throws ResourceNotFoundException;
}
