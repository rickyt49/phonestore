package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.CustomerAndTotalPurchaseDto;
import com.axonactive.phonestore.service.dto.PhoneModelAndAmountDto;
import com.axonactive.phonestore.service.dto.StoreDto;

import java.util.List;

public interface StoreService {
    List<StoreDto> getAll();

    StoreDto findById(Integer id) throws EntityNotFoundException;

    StoreDto save(StoreRequest storeRequest) throws EntityNotFoundException;

    void delete(Integer id);

    StoreDto update(Integer id, StoreRequest storeRequest) throws EntityNotFoundException;

    List<PhoneModelAndAmountDto> getPhoneModelAndItsAmountByStoreId(Integer storeId);
    List<CustomerAndTotalPurchaseDto> getCustomerTotalPurchaseReportByStoreId(Integer storeId);

}
