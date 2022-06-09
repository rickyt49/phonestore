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
    public Optional<Owner> findById(Integer id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Integer id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner update(Integer id, Owner ownerDetails) throws ResourceNotFoundException {
        Owner updatedOwner = ownerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + id));
        updatedOwner.setFullName(ownerDetails.getFullName());
        updatedOwner.setAddress(ownerDetails.getAddress());
        updatedOwner.setPhoneNumber(ownerDetails.getPhoneNumber());
        return ownerRepository.save(updatedOwner);
    }


}
