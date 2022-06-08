package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> getAll();

    Optional<Owner> findOwnerById(Integer id);

    Owner saveOwner(Owner owner);

    void deleteOwner(Integer id);

    Owner updateAssignment(Integer id, Owner owner) throws ResourceNotFoundException;
}
