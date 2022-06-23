package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;

import java.util.List;

public interface PhysicalPhoneService {
    List<PhysicalPhoneDto> getAll();

    PhysicalPhoneDto findById(Integer id) ;

    PhysicalPhoneDto save(PhysicalPhoneRequest physicalPhoneRequest) ;

    void delete(Integer id);

    PhysicalPhoneDto update(Integer id, PhysicalPhoneRequest physicalPhoneRequest) ;

    PhysicalPhoneDto findByImei(String imei) ;

    List<PhysicalPhoneDto> findAllAvailablePhone();

    List<PhysicalPhoneDto> findAllAvailablePhoneByStore(Integer storeId);


}
