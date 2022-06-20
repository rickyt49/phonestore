package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;

import java.util.List;

public interface PhysicalPhoneService {
    List<PhysicalPhoneDto> getAll();

    PhysicalPhoneDto findById(Integer id) throws EntityNotFoundException;

    PhysicalPhoneDto save(PhysicalPhoneRequest physicalPhoneRequest) throws EntityNotFoundException;

    void delete(Integer id);

    PhysicalPhoneDto update(Integer id, PhysicalPhoneRequest physicalPhoneRequest) throws EntityNotFoundException;

    PhysicalPhoneDto findByImei(String imei) throws EntityNotFoundException;

    List<PhysicalPhoneDto> findAllAvailablePhone();

    List<PhysicalPhoneDto> findAllAvailablePhoneByStore(Integer storeId);


}
