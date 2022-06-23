package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.repository.PhysicalPhoneRepository;
import com.axonactive.phonestore.repository.SpecificationRepository;
import com.axonactive.phonestore.repository.StoreRepository;
import com.axonactive.phonestore.repository.SupplierRepository;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.mapper.PhysicalPhoneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PhysicalPhoneServiceImpl implements PhysicalPhoneService {
    @Autowired
    private PhysicalPhoneRepository physicalPhoneRepository;
    @Autowired
    private PhysicalPhoneMapper physicalPhoneMapper;
    @Autowired
    private SpecificationRepository specificationRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Override

    public List<PhysicalPhoneDto> getAll() {
        log.info("Searching for all phones");
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAll());
    }

    @Override
    public PhysicalPhoneDto findById(Integer id) {
        log.info("Searching for phone has id {}", id);
        return physicalPhoneMapper.toDto(physicalPhoneRepository.findById(id).orElseThrow(BusinessLogicException::PhoneNotFound));
    }

    @Override
    public PhysicalPhoneDto save(PhysicalPhoneRequest physicalPhoneRequest) {
        PhysicalPhone addedPhone = new PhysicalPhone();
        addedPhone.setImei(physicalPhoneRequest.getImei());
        addedPhone.setColor(physicalPhoneRequest.getColor());
        addedPhone.setMemorySize(physicalPhoneRequest.getMemorySize());
        addedPhone.setPhoneStatus(physicalPhoneRequest.getPhoneStatus());
        addedPhone.setCondition(physicalPhoneRequest.getCondition());
        addedPhone.setWarranty(physicalPhoneRequest.getWarranty());
        addedPhone.setImportPrice(physicalPhoneRequest.getImportPrice());
        addedPhone.setImportDate(physicalPhoneRequest.getImportDate());

        log.info("Searching for store has id {}", physicalPhoneRequest.getStoreId());
        addedPhone.setStore(storeRepository.findById(physicalPhoneRequest.getStoreId()).orElseThrow(BusinessLogicException::StoreNotFound));

        log.info("Searching for supplier has id {}", physicalPhoneRequest.getSupplierId());
        addedPhone.setSupplier(supplierRepository.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(BusinessLogicException::SupplierNotFound));

        log.info("Searching for specification of phone model {}", physicalPhoneRequest.getSpecificationModel());
        addedPhone.setSpecification(specificationRepository.findByModelIgnoreCase(physicalPhoneRequest.getSpecificationModel()).orElseThrow(BusinessLogicException::SpecificationNotFound));

        return physicalPhoneMapper.toDto(physicalPhoneRepository.save(addedPhone));
    }

    @Override
    public void delete(Integer id) {
        log.info("Searching for phone has id {}", id);
        physicalPhoneRepository.findById(id).orElseThrow(BusinessLogicException::PhoneNotFound);
        physicalPhoneRepository.deleteById(id);
    }

    @Override
    public PhysicalPhoneDto update(Integer id, PhysicalPhoneRequest physicalPhoneRequest) {
        log.info("Searching for phone has id {}", id);
        PhysicalPhone updatedPhysicalPhone = physicalPhoneRepository.findById(id).orElseThrow(BusinessLogicException::PhoneNotFound);

        updatedPhysicalPhone.setImei(physicalPhoneRequest.getImei());
        updatedPhysicalPhone.setColor(physicalPhoneRequest.getColor());
        updatedPhysicalPhone.setPhoneStatus(physicalPhoneRequest.getPhoneStatus());
        updatedPhysicalPhone.setCondition(physicalPhoneRequest.getCondition());
        updatedPhysicalPhone.setWarranty(physicalPhoneRequest.getWarranty());
        updatedPhysicalPhone.setImportPrice(physicalPhoneRequest.getImportPrice());
        updatedPhysicalPhone.setImportDate(physicalPhoneRequest.getImportDate());
        log.info("Searching for store has id {}", physicalPhoneRequest.getStoreId());
        updatedPhysicalPhone.setStore(storeRepository.findById(physicalPhoneRequest.getStoreId()).orElseThrow(BusinessLogicException::StoreNotFound));

        log.info("Searching for supplier has id {}", physicalPhoneRequest.getSupplierId());
        updatedPhysicalPhone.setSupplier(supplierRepository.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(BusinessLogicException::SupplierNotFound));

        log.info("Searching for specification of phone model {}", physicalPhoneRequest.getSpecificationModel());
        updatedPhysicalPhone.setSpecification(specificationRepository.findByModelIgnoreCase(physicalPhoneRequest.getSpecificationModel()).orElseThrow(BusinessLogicException::SpecificationNotFound));

        return physicalPhoneMapper.toDto(physicalPhoneRepository.save(updatedPhysicalPhone));
    }

    @Override
    public PhysicalPhoneDto findByImei(String imei) {
        log.info("Searching for phone has imei {}", imei);
        return physicalPhoneMapper.toDto(physicalPhoneRepository.findByImei(imei).orElseThrow(BusinessLogicException::PhoneNotFound));
    }

    @Override
    public List<PhysicalPhoneDto> findAllAvailablePhone() {
        log.info("Searching for all available phones");
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAllAvailablePhone());
    }

    @Override
    public List<PhysicalPhoneDto> findAllAvailablePhoneByStore(Integer storeId) {
        log.info("Searching for all available phone in store has id {}", storeId);
        return physicalPhoneMapper.toDtos(physicalPhoneRepository.findAllAvailablePhoneByStore(storeId));
    }

}
