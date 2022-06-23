package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.OwnerRequest;
import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.OwnerRepository;
import com.axonactive.phonestore.service.OwnerService;
import com.axonactive.phonestore.service.dto.OwnerDto;
import com.axonactive.phonestore.service.mapper.OwnerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<OwnerDto> getAll() {

        log.info("Searching for all owner");
        return ownerMapper.toDtos(ownerRepository.findAll());
    }

    @Override
    public OwnerDto findById(Integer id) {
        log.info("Searching for owner has id {}", id);
        return ownerMapper.toDto(ownerRepository.findById(id).orElseThrow(BusinessLogicException::OwnerNotFound));
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
        log.info("Searching for owner has id {}", id);
        ownerRepository.findById(id).orElseThrow(BusinessLogicException::OwnerNotFound);
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDto update(Integer id, OwnerRequest ownerRequest) {
        log.info("Searching for owner has id {}", id);
        Owner updatedOwner = ownerRepository.findById(id).orElseThrow(BusinessLogicException::OwnerNotFound);

        updatedOwner.setFullName(ownerRequest.getFullName());
        updatedOwner.setAddress(ownerRequest.getAddress());
        updatedOwner.setPhoneNumber(ownerRequest.getPhoneNumber());
        return ownerMapper.toDto(ownerRepository.save(updatedOwner));
    }

    @Override
    public List<Owner> findByPhoneNumberContaining(String phoneNumber) {
        log.info("Searching for employee with phone number containing {}", phoneNumber);
        if (!(phoneNumber.matches("[0-9]+"))) {
            throw BusinessLogicException.PhoneNumberBadRequest();
        } else {
            return ownerRepository.findByPhoneNumberContaining(phoneNumber);
        }
    }

    @Override
    public List<Owner> findByFullNameContaining(String name) {
        log.info("Searching for employee with name containing {}", name);
        return ownerRepository.findByFullNameContaining(name);
    }


}
