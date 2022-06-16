package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicalPhoneServiceImpl implements PhysicalPhoneService {
    @Autowired
    PhysicalPhoneRepository physicalPhoneRepository;

    @Override
    public List<PhysicalPhone> getAll() {
        return physicalPhoneRepository.findAll();
    }

    @Override
    public Optional<PhysicalPhone> findById(Integer id) {
        return physicalPhoneRepository.findById(id);
    }

    @Override
    public PhysicalPhone save(PhysicalPhone physicalPhone) {
        return physicalPhoneRepository.save(physicalPhone);
    }

    @Override
    public void delete(Integer id) {
        physicalPhoneRepository.deleteById(id);
    }

    @Override
    public PhysicalPhone update(Integer id, PhysicalPhone physicalPhoneDetails) throws ResourceNotFoundException {
        PhysicalPhone updatedPhysicalPhone = physicalPhoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + id));
        updatedPhysicalPhone.setImei(physicalPhoneDetails.getImei());
        updatedPhysicalPhone.setColor(physicalPhoneDetails.getColor());
        updatedPhysicalPhone.setPhoneStatus(physicalPhoneDetails.getPhoneStatus());
        updatedPhysicalPhone.setCondition(physicalPhoneDetails.getCondition());
        updatedPhysicalPhone.setWarranty(physicalPhoneDetails.getWarranty());
        updatedPhysicalPhone.setImportPrice(physicalPhoneDetails.getImportPrice());
        updatedPhysicalPhone.setImportDate(physicalPhoneDetails.getImportDate());
        return physicalPhoneRepository.save(updatedPhysicalPhone);
    }

    @Override
    public Optional<PhysicalPhone> findByImei(String imei) {
        return physicalPhoneRepository.findByImei(imei);
    }

    @Override
    public List<PhysicalPhone> findAllAvailablePhone() {
        return physicalPhoneRepository.findAllAvailablePhone();
    }

    @Override
    public List<PhysicalPhone> findAllAvailablePhoneByStore(Integer storeId) {
        return physicalPhoneRepository.findAllAvailablePhoneByStore(storeId);
    }
}
