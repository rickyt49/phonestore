package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.OwnerRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.CustomerAndTotalPurchaseDto;
import com.axonactive.phonestore.service.dto.PhoneModelAndAmountDto;
import com.axonactive.phonestore.service.dto.StoreDto;
import com.axonactive.phonestore.service.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
    public StoreDto findById(Integer id) throws EntityNotFoundException {
        return storeMapper.toDto(storeRepository.findById(id).orElseThrow(BusinessLogicException::StoreNotFound));
    }

    @Override
    public StoreDto save(StoreRequest storeRequest) throws EntityNotFoundException {
        Store createdStore = new Store();
        createdStore.setName(storeRequest.getName());
        createdStore.setPhoneNumber(storeRequest.getPhoneNumber());
        createdStore.setCity(storeRequest.getCity());
        createdStore.setOwner(ownerRepository.findById(storeRequest.getOwnerId()).orElseThrow(BusinessLogicException::OwnerNotFound));
        createdStore.setAddress(storeRequest.getAddress());
        return storeMapper.toDto(storeRepository.save(createdStore));
    }

    @Override
    public void delete(Integer id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreDto update(Integer id, StoreRequest storeRequest) {
        Store updatedStore = storeRepository.findById(id).orElseThrow(BusinessLogicException::StoreNotFound);
        updatedStore.setOwner(ownerRepository.findById(storeRequest.getOwnerId()).orElseThrow(BusinessLogicException::OwnerNotFound));
        updatedStore.setName(storeRequest.getName());
        updatedStore.setAddress(storeRequest.getAddress());
        updatedStore.setCity(storeRequest.getCity());
        updatedStore.setPhoneNumber(storeRequest.getPhoneNumber());
        return storeMapper.toDto(storeRepository.save(updatedStore));
    }

    @Override
    public List<PhoneModelAndAmountDto> getPhoneModelAndItsAmountByStoreId(Integer storeId) {
        return storeRepository.getPhoneModelAndItsAmountByStoreId(storeId);
    }

    @Override
    public List<CustomerAndTotalPurchaseDto> getCustomerTotalPurchaseReportByStoreId(Integer storeId) {
        return storeRepository.getCustomerTotalPurchaseReportByStoreId(storeId);
    }


}
