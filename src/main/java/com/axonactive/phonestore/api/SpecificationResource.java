package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.SpecificationRequest;
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
        return ResponseEntity.ok(specificationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificationDto> findSpecificationById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(specificationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> add(@RequestBody SpecificationRequest specificationRequest) {
        SpecificationDto createdSpecification = specificationService.save(specificationRequest);
        return ResponseEntity.created(URI.create(SpecificationResource.PATH + "/" + createdSpecification.getId())).body(createdSpecification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecificationDto> update(@PathVariable(value = "id") Integer id, @RequestBody SpecificationRequest specificationRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(specificationService.update(id, specificationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        specificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
