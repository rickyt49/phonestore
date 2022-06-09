package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreRepository storeRepository;

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(Integer id) {
        return storeRepository.findById(id);
    }

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void delete(Integer id) {
        storeRepository.deleteById(id);
    }

    @Override
    public Store update(Integer id, Store storeDetails) throws ResourceNotFoundException {
        Store updatedStore = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + id));
        updatedStore.setOwner(storeDetails.getOwner());
        updatedStore.setName(storeDetails.getName());
        updatedStore.setAddress(storeDetails.getAddress());
        updatedStore.setCity(storeDetails.getCity());
        updatedStore.setPhoneNumber(storeDetails.getPhoneNumber());
        return storeRepository.save(updatedStore);
    }
}
