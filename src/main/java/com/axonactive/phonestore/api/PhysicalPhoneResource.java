package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.mapper.PhysicalPhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(PhysicalPhoneResource.PATH)
public class PhysicalPhoneResource {
    public static final String PATH = "/api/phones";
    @Autowired
    private PhysicalPhoneService physicalPhoneService;

    @Autowired
    private PhysicalPhoneMapper physicalPhoneMapper;


    @GetMapping
    public ResponseEntity<List<PhysicalPhoneDto>> getAll() {
        return ResponseEntity.ok(physicalPhoneService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> findPhoneById(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok(physicalPhoneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PhysicalPhoneDto> add(@RequestBody PhysicalPhoneRequest physicalPhoneRequest) throws EntityNotFoundException {
        PhysicalPhoneDto addedPhone = physicalPhoneService.save(physicalPhoneRequest);
        return ResponseEntity.created(URI.create(PhysicalPhoneResource.PATH + "/" + addedPhone.getId())).body(addedPhone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        physicalPhoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> update(@PathVariable(value = "id") Integer id, @RequestBody PhysicalPhoneRequest physicalPhoneRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(physicalPhoneService.update(id, physicalPhoneRequest));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PhysicalPhoneDto>> getAvailablePhone() {
        return ResponseEntity.ok(physicalPhoneService.findAllAvailablePhone());
    }
}
