package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.SpecificationRequest;
import com.axonactive.phonestore.entity.Specification;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.SpecificationService;
import com.axonactive.phonestore.service.dto.SpecificationDto;
import com.axonactive.phonestore.service.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(SpecificationResource.PATH)
public class SpecificationResource {
    public static final String PATH = "/api/specifications";
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecificationMapper specificationMapper;

    @GetMapping
    public ResponseEntity<List<SpecificationDto>> getAll() {
        return ResponseEntity.ok(specificationMapper.toDtos(specificationService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificationDto> findSpecificationById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(specificationMapper.toDto(specificationService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> add(@RequestBody SpecificationRequest specificationRequest) {
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
        Specification createdSpecification = specificationService.save(specification);
        return ResponseEntity.created(URI.create(SpecificationResource.PATH + "/" + createdSpecification.getId())).body(specificationMapper.toDto(createdSpecification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecificationDto> update(@PathVariable(value = "id") Integer id, @RequestBody SpecificationRequest specificationRequest) throws ResourceNotFoundException {
        Specification updatedSpecification = new Specification();
        updatedSpecification.setModel(specificationRequest.getModel());
        updatedSpecification.setCpu(specificationRequest.getCpu());
        updatedSpecification.setGpu(specificationRequest.getGpu());
        updatedSpecification.setRam(specificationRequest.getRam());
        updatedSpecification.setScreenType(specificationRequest.getScreenType());
        updatedSpecification.setScreenResolution(specificationRequest.getScreenResolution());
        updatedSpecification.setMainCamera(specificationRequest.getMainCamera());
        updatedSpecification.setSelfieCamera(specificationRequest.getSelfieCamera());
        updatedSpecification.setBatteryCapacity(specificationRequest.getBatteryCapacity());
        updatedSpecification.setManufacturer(specificationRequest.getManufacturer());
        updatedSpecification.setSelfieCamera(specificationRequest.getSelfieCamera());
        updatedSpecification.setManufacturerStatus(specificationRequest.getManufacturerStatus());
        return ResponseEntity.ok(specificationMapper.toDto(specificationService.update(id, updatedSpecification)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        specificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
