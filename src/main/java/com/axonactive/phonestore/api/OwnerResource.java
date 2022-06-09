package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.OwnerRequest;
import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.OwnerService;
import com.axonactive.phonestore.service.dto.OwnerDto;
import com.axonactive.phonestore.service.mapper.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(OwnerResource.PATH)
public class OwnerResource {
    public static final String PATH = "/api/owner";
    @Autowired
    OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        return ResponseEntity.ok(OwnerMapper.INSTANCE.toDtos(ownerService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Owner owner = ownerService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + id));
        return ResponseEntity.ok(OwnerMapper.INSTANCE.toDto(owner));
    }

    @PostMapping
    public ResponseEntity<OwnerDto> add(@RequestBody OwnerRequest ownerRequest) {
        Owner createdOwner = new Owner();
        createdOwner.setFullName(ownerRequest.getFullName());
        createdOwner.setPhoneNumber(ownerRequest.getPhoneNumber());
        createdOwner.setAddress(ownerRequest.getAddress());
        ownerService.save(createdOwner);
        return ResponseEntity.created(URI.create(OwnerResource.PATH + "/" + createdOwner.getId())).body(OwnerMapper.INSTANCE.toDto(createdOwner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> update(@PathVariable(value = "id") Integer id, @RequestBody OwnerRequest ownerRequest) throws ResourceNotFoundException {
        Owner updatedOwner = new Owner();
        updatedOwner.setFullName(ownerRequest.getFullName());
        updatedOwner.setAddress(ownerRequest.getAddress());
        updatedOwner.setPhoneNumber(ownerRequest.getPhoneNumber());
        return ResponseEntity.ok(OwnerMapper.INSTANCE.toDto(ownerService.update(id, updatedOwner)));
    }
}
