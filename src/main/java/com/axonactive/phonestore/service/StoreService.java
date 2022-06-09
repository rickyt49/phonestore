package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> getAll();

    Optional<Store> findById(Integer id);

    Store save(Store store);

    void delete(Integer id);

    Store update(Integer id, Store storeDetails) throws ResourceNotFoundException;

}
