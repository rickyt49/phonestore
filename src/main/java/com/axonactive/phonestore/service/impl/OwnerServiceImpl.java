package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.OwnerRepository;
import com.axonactive.phonestore.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findOwnerById(Integer id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Integer id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner updateAssignment(Integer id, Owner owner) throws ResourceNotFoundException {
        Owner updatedOwner = ownerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + id));
        if (validationForUpdateOwner(owner) == 1) {
            updatedOwner.setFullName(owner.getFullName());
            updatedOwner.setAddress(owner.getAddress());
            updatedOwner.setPhoneNumber(owner.getPhoneNumber());
        }
        return ownerRepository.save(updatedOwner);
    }

    private int validationForUpdateOwner(Owner owner) {
        if (owner.getFullName() != null && !(owner.getFullName().isEmpty())) {
            return 2;
        }
        if (owner.getAddress() != null && !(owner.getAddress().isEmpty())) {
            return 3;
        }
        if (owner.getPhoneNumber() != null && !(owner.getPhoneNumber().isEmpty())) {
            return 4;
        } else return 1;
    }
}
