package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.api.request.SpecificationRequest;
import com.axonactive.phonestore.entity.Specification;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.SpecificationRepository;
import com.axonactive.phonestore.service.SpecificationService;
import com.axonactive.phonestore.service.dto.SpecificationDto;
import com.axonactive.phonestore.service.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    SpecificationRepository specificationRepository;

    @Autowired
    SpecificationMapper specificationMapper;

    @Override
    public List<SpecificationDto> getAll() {
        return specificationMapper.toDtos(specificationRepository.findAll());
    }

    @Override
    public SpecificationDto findById(Integer id) throws ResourceNotFoundException {
        return specificationMapper.toDto(specificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + id)));
    }

    @Override
    public SpecificationDto save(SpecificationRequest specificationRequest) {
        Specification specification = new Specification();
        specification.setModel(specificationRequest.getModel());
        specification.setCpu(specificationRequest.getCpu());
        specification.setGpu(specificationRequest.getGpu());
        specification.setRam(specificationRequest.getRam());
        specification.setScreenType(specificationRequest.getScreenType());
        specification.setScreenResolution(specificationRequest.getScreenResolution());
        specification.setMainCamera(specificationRequest.getMainCamera());
        specification.setSelfieCamera(specificationRequest.getSelfieCamera());
        specification.setBatteryCapacity(specificationRequest.getBatteryCapacity());
        specification.setManufacturer(specificationRequest.getManufacturer());
        specification.setSelfieCamera(specificationRequest.getSelfieCamera());
        specification.setManufacturerStatus(specificationRequest.getManufacturerStatus());
        return specificationMapper.toDto(specificationRepository.save(specification));
    }

    @Override
    public void delete(Integer id) {
        specificationRepository.deleteById(id);
    }

    @Override
    public SpecificationDto update(Integer id, SpecificationRequest specificationRequest) throws ResourceNotFoundException {
        Specification updatedSpec = specificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + id));
        updatedSpec.setModel(specificationRequest.getModel());
        updatedSpec.setCpu(specificationRequest.getCpu());
        updatedSpec.setGpu(specificationRequest.getGpu());
        updatedSpec.setRam(specificationRequest.getRam());
        updatedSpec.setOs(specificationRequest.getOs());
        updatedSpec.setScreenType(specificationRequest.getScreenType());
        updatedSpec.setScreenResolution(specificationRequest.getScreenResolution());
        updatedSpec.setMainCamera(specificationRequest.getMainCamera());
        updatedSpec.setSelfieCamera(specificationRequest.getSelfieCamera());
        updatedSpec.setBatteryCapacity(specificationRequest.getBatteryCapacity());
        updatedSpec.setManufacturer(specificationRequest.getManufacturer());
        updatedSpec.setManufacturerStatus(specificationRequest.getManufacturerStatus());
        return specificationMapper.toDto(specificationRepository.save(updatedSpec));
    }
}
