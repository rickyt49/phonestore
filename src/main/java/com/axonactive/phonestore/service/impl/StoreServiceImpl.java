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
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<StoreDto> getAll() {
        log.info("Searching for all stores");
        return storeMapper.toDtos(storeRepository.findAll());
    }

    @Override
    public StoreDto findById(Integer id) {
        log.info("Searching for store has id {}", id);
        return storeMapper.toDto(storeRepository.findById(id).orElseThrow(BusinessLogicException::StoreNotFound));
    }

    @Override
    public StoreDto save(StoreRequest storeRequest) {
        Store createdStore = new Store();
        createdStore.setName(storeRequest.getName());
        createdStore.setPhoneNumber(storeRequest.getPhoneNumber());
        createdStore.setCity(storeRequest.getCity());

        log.info("Searching for owner has id {}", storeRequest.getOwnerId());
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
        log.info("Searching for store has id {}", id);
        Store updatedStore = storeRepository.findById(id).orElseThrow(BusinessLogicException::StoreNotFound);

        log.info("Searching for owner has id {}", storeRequest.getOwnerId());
        updatedStore.setOwner(ownerRepository.findById(storeRequest.getOwnerId()).orElseThrow(BusinessLogicException::OwnerNotFound));
        updatedStore.setName(storeRequest.getName());
        updatedStore.setAddress(storeRequest.getAddress());
        updatedStore.setCity(storeRequest.getCity());
        updatedStore.setPhoneNumber(storeRequest.getPhoneNumber());
        return storeMapper.toDto(storeRepository.save(updatedStore));
    }

    @Override
    public List<PhoneModelAndAmountDto> getPhoneModelAndItsAmountByStoreId(Integer storeId) {
        log.info("Getting phone store report of store has id {}", storeId);
        return storeRepository.getPhoneModelAndItsAmountByStoreId(storeId);
    }

    @Override
    public List<CustomerAndTotalPurchaseDto> getCustomerTotalPurchaseReportByStoreId(Integer storeId) {
        log.info("Getting customer report of store has id {}", storeId);
        return storeRepository.getCustomerTotalPurchaseReportByStoreId(storeId);
    }


}
