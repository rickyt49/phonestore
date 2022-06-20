package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.OwnerRequest;
import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.OwnerRepository;
import com.axonactive.phonestore.service.OwnerService;
import com.axonactive.phonestore.service.dto.OwnerDto;
import com.axonactive.phonestore.service.mapper.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<OwnerDto> getAll() {
        return ownerMapper.toDtos(ownerRepository.findAll());
    }

    @Override
    public OwnerDto findById(Integer id) throws EntityNotFoundException {
        return ownerMapper.toDto(ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner Not Found: " + id)));
    }

    @Override
    public OwnerDto save(OwnerRequest ownerRequest) {
        Owner owner = new Owner();
        owner.setFullName(ownerRequest.getFullName());
        owner.setPhoneNumber(ownerRequest.getPhoneNumber());
        owner.setAddress(ownerRequest.getAddress());
        return ownerMapper.toDto(ownerRepository.save(owner));
    }

    @Override
    public void delete(Integer id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDto update(Integer id, OwnerRequest ownerRequest) throws EntityNotFoundException {
        Owner updatedOwner = ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found: " + id));
        updatedOwner.setFullName(ownerRequest.getFullName());
        updatedOwner.setAddress(ownerRequest.getAddress());
        updatedOwner.setPhoneNumber(ownerRequest.getPhoneNumber());
        return ownerMapper.toDto(ownerRepository.save(updatedOwner));
    }

    @Override
    public List<Owner> findByPhoneNumberContaining(String phoneNumber) {
        return ownerRepository.findByPhoneNumberContaining(phoneNumber);
    }

    @Override
    public List<Owner> findByFullNameContaining(String name) {
        return ownerRepository.findByFullNameContaining(name);
    }


}
