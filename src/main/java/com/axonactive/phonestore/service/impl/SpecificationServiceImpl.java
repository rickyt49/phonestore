package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.entity.Specification;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.repository.SpecificationRepository;
import com.axonactive.phonestore.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    SpecificationRepository specificationRepository;

    @Override
    public List<Specification> getAll() {
        return specificationRepository.findAll();
    }

    @Override
    public Optional<Specification> findById(Integer id) {
        return specificationRepository.findById(id);
    }

    @Override
    public Specification save(Specification specification) {
        return specificationRepository.save(specification);
    }

    @Override
    public void delete(Integer id) {
        specificationRepository.deleteById(id);
    }

    @Override
    public Specification update(Integer id, Specification specificationDetails) throws ResourceNotFoundException {
        Specification updatedSpec = specificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + id));
        updatedSpec.setModel(specificationDetails.getModel());
        updatedSpec.setCpu(specificationDetails.getCpu());
        updatedSpec.setGpu(specificationDetails.getGpu());
        updatedSpec.setRam(specificationDetails.getRam());
        updatedSpec.setOs(specificationDetails.getOs());
        updatedSpec.setScreenType(specificationDetails.getScreenType());
        updatedSpec.setScreenResolution(specificationDetails.getScreenResolution());
        updatedSpec.setMainCamera(specificationDetails.getMainCamera());
        updatedSpec.setSelfieCamera(specificationDetails.getSelfieCamera());
        updatedSpec.setBatteryCapacity(specificationDetails.getBatteryCapacity());
        updatedSpec.setManufacturer(specificationDetails.getManufacturer());
        updatedSpec.setManufacturerStatus(specificationDetails.getManufacturerStatus());
        return specificationRepository.save(updatedSpec);
    }
}
