package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.OwnerRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.StoreDto;
import com.axonactive.phonestore.service.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreMapper storeMapper;

    @Autowired
    OwnerRepository ownerRepository;

    @Override
    public List<StoreDto> getAll() {
        return storeMapper.toDtos(storeRepository.findAll());
    }

    @Override
    public StoreDto findById(Integer id) throws ResourceNotFoundException {
        return storeMapper.toDto(storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + id)));
    }

    @Override
    public StoreDto save(StoreRequest storeRequest) throws ResourceNotFoundException {
        Store createdStore = new Store();
        createdStore.setName(storeRequest.getName());
        createdStore.setPhoneNumber(storeRequest.getPhoneNumber());
        createdStore.setCity(storeRequest.getCity());
        createdStore.setOwner(ownerRepository.findById(storeRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner Not Found: " + storeRequest.getOwnerId())));
        createdStore.setAddress(storeRequest.getAddress());
        return storeMapper.toDto(storeRepository.save(createdStore));
    }

    @Override
    public void delete(Integer id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreDto update(Integer id, StoreRequest storeRequest) throws ResourceNotFoundException {
        Store updatedStore = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + id));
        updatedStore.setOwner(ownerRepository.findById(storeRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner Not Found: " + storeRequest.getOwnerId())));
        updatedStore.setName(storeRequest.getName());
        updatedStore.setAddress(storeRequest.getAddress());
        updatedStore.setCity(storeRequest.getCity());
        updatedStore.setPhoneNumber(storeRequest.getPhoneNumber());
        return storeMapper.toDto(storeRepository.save(updatedStore));
    }
}
