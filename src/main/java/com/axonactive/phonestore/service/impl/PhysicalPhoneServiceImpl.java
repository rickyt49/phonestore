package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.repository.SpecificationRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.repository.SupplierRepository;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.dto.PhoneModelAndAmountDto;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.mapper.PhysicalPhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalPhoneServiceImpl implements PhysicalPhoneService {
    @Autowired
    PhysicalPhoneRepository physicalPhoneRepository;

    @Autowired
    PhysicalPhoneMapper physicalPhoneMapper;
    @Autowired
    private SpecificationRepository specificationRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Override

    public List<PhysicalPhoneDto> getAll() {
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAll());
    }

    @Override
    public PhysicalPhoneDto findById(Integer id) throws ResourceNotFoundException {
        return physicalPhoneMapper.toDto(physicalPhoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + id)));
    }

    @Override
    public PhysicalPhoneDto save(PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException {
        PhysicalPhone addedPhone = new PhysicalPhone();
        addedPhone.setImei(physicalPhoneRequest.getImei());
        addedPhone.setColor(physicalPhoneRequest.getColor());
        addedPhone.setMemorySize(physicalPhoneRequest.getMemorySize());
        addedPhone.setPhoneStatus(physicalPhoneRequest.getPhoneStatus());
        addedPhone.setCondition(physicalPhoneRequest.getCondition());
        addedPhone.setWarranty(physicalPhoneRequest.getWarranty());
        addedPhone.setImportPrice(physicalPhoneRequest.getImportPrice());
        addedPhone.setImportDate(physicalPhoneRequest.getImportDate());
        addedPhone.setStore(storeRepository.findById(physicalPhoneRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + physicalPhoneRequest.getStoreId())));
        addedPhone.setSupplier(supplierRepository.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + physicalPhoneRequest.getSupplierId())));
        addedPhone.setSpecification(specificationRepository.findByModelIgnoreCase(physicalPhoneRequest.getSpecificationModel()).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + physicalPhoneRequest.getSpecificationModel())));

        return physicalPhoneMapper.toDto(physicalPhoneRepository.save(addedPhone));
    }

    @Override
    public void delete(Integer id) {
        physicalPhoneRepository.deleteById(id);
    }

    @Override
    public PhysicalPhoneDto update(Integer id, PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException {
        PhysicalPhone updatedPhysicalPhone = physicalPhoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + id));
        updatedPhysicalPhone.setImei(physicalPhoneRequest.getImei());
        updatedPhysicalPhone.setColor(physicalPhoneRequest.getColor());
        updatedPhysicalPhone.setPhoneStatus(physicalPhoneRequest.getPhoneStatus());
        updatedPhysicalPhone.setCondition(physicalPhoneRequest.getCondition());
        updatedPhysicalPhone.setWarranty(physicalPhoneRequest.getWarranty());
        updatedPhysicalPhone.setImportPrice(physicalPhoneRequest.getImportPrice());
        updatedPhysicalPhone.setImportDate(physicalPhoneRequest.getImportDate());
        updatedPhysicalPhone.setStore(storeRepository.findById(physicalPhoneRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + physicalPhoneRequest.getStoreId())));
        updatedPhysicalPhone.setSupplier(supplierRepository.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + physicalPhoneRequest.getSupplierId())));
        updatedPhysicalPhone.setSpecification(specificationRepository.findByModelIgnoreCase(physicalPhoneRequest.getSpecificationModel()).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + physicalPhoneRequest.getSpecificationModel())));
        return physicalPhoneMapper.toDto(physicalPhoneRepository.save(updatedPhysicalPhone));
    }

    @Override
    public PhysicalPhoneDto findByImei(String imei) throws ResourceNotFoundException {
        return physicalPhoneMapper.toDto(physicalPhoneRepository.findByImei(imei).orElseThrow(() -> new ResourceNotFoundException("Phone not found with IMEI :" + imei)));
    }

    @Override
    public List<PhysicalPhoneDto> findAllAvailablePhone() {
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAllAvailablePhone());
    }

    @Override
    public List<PhysicalPhoneDto> findAllAvailablePhoneByStore(Integer storeId) {
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAllAvailablePhoneByStore(storeId));
    }

    @Override
    public List<PhoneModelAndAmountDto> getPhoneModelAndItsAmount() {
        return physicalPhoneRepository.getPhoneModelAndItsAmount();
    }
}
