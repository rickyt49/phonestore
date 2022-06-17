package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;

import java.util.List;

public interface PhysicalPhoneService {
    List<PhysicalPhoneDto> getAll();

    PhysicalPhoneDto findById(Integer id) throws ResourceNotFoundException;

    PhysicalPhoneDto save(PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException;

    void delete(Integer id);

    PhysicalPhoneDto update(Integer id, PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException;

    PhysicalPhoneDto findByImei(String imei) throws ResourceNotFoundException;

    List<PhysicalPhoneDto> findAllAvailablePhone();

    List<PhysicalPhoneDto> findAllAvailablePhoneByStore(Integer storeId);

}
