package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.mapper.PhysicalPhoneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(PhysicalPhoneResource.PATH)
public class PhysicalPhoneResource {
    public static final String PATH = "/api/phones";
    @Autowired
    private PhysicalPhoneService physicalPhoneService;


    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<PhysicalPhoneDto>> getAll() {
        return ResponseEntity.ok(physicalPhoneService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> findPhoneById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(physicalPhoneService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<PhysicalPhoneDto> add(@Valid @RequestBody PhysicalPhoneRequest physicalPhoneRequest) {
        PhysicalPhoneDto addedPhone = physicalPhoneService.save(physicalPhoneRequest);
        return ResponseEntity.created(URI.create(PhysicalPhoneResource.PATH + "/" + addedPhone.getId())).body(addedPhone);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        physicalPhoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> update(@Valid @PathVariable(value = "id") Integer id, @RequestBody PhysicalPhoneRequest physicalPhoneRequest) {
        return ResponseEntity.ok(physicalPhoneService.update(id, physicalPhoneRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/available")
    public ResponseEntity<List<PhysicalPhoneDto>> getAvailablePhone() {
        return ResponseEntity.ok(physicalPhoneService.findAllAvailablePhone());
    }
}
