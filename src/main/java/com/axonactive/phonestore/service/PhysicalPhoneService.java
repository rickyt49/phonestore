package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PhysicalPhoneService {
    List<PhysicalPhone> getAll();

    Optional<PhysicalPhone> findById(Integer id);

    PhysicalPhone save(PhysicalPhone physicalPhone);

    void delete(Integer id);

    PhysicalPhone update(Integer id, PhysicalPhone physicalPhoneDetails) throws ResourceNotFoundException;

    Optional<PhysicalPhone> findByImei(String imei);

    List<PhysicalPhone> findAllAvailablePhone();

    List<PhysicalPhone> findAllAvailablePhoneByStore(Integer storeId);

}
