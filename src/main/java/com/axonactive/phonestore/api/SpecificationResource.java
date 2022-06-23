package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.SpecificationRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.SpecificationService;
import com.axonactive.phonestore.service.dto.SpecificationDto;
import com.axonactive.phonestore.service.mapper.SpecificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(SpecificationResource.PATH)
public class SpecificationResource {
    public static final String PATH = "/api/specifications";
    @Autowired
    private SpecificationService specificationService;

    @GetMapping
    public ResponseEntity<List<SpecificationDto>> getAll() {
        return ResponseEntity.ok(specificationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificationDto> findSpecificationById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(specificationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> add(@Valid @RequestBody SpecificationRequest specificationRequest) {
        SpecificationDto createdSpecification = specificationService.save(specificationRequest);
        return ResponseEntity.created(URI.create(SpecificationResource.PATH + "/" + createdSpecification.getId())).body(createdSpecification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecificationDto> update(@Valid @PathVariable(value = "id") Integer id, @RequestBody SpecificationRequest specificationRequest) {
        return ResponseEntity.ok(specificationService.update(id, specificationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        specificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
