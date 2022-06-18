package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.dto.StoreDto;

import java.util.List;

public interface StoreService {
    List<StoreDto> getAll();

    StoreDto findById(Integer id) throws ResourceNotFoundException;

    StoreDto save(StoreRequest storeRequest) throws ResourceNotFoundException;

    void delete(Integer id);

    StoreDto update(Integer id, StoreRequest storeRequest) throws ResourceNotFoundException;

}
