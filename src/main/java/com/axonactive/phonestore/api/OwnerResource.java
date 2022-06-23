package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.OwnerRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.OwnerService;
import com.axonactive.phonestore.service.dto.OwnerDto;
import com.axonactive.phonestore.service.mapper.OwnerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(OwnerResource.PATH)
public class OwnerResource {
    public static final String PATH = "/api/owners";
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerMapper ownerMapper;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        return ResponseEntity.ok(ownerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> findOwnerById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OwnerDto> add(@Valid @RequestBody OwnerRequest ownerRequest) {
        OwnerDto createdOwner = ownerService.save(ownerRequest);
        return ResponseEntity.created(URI.create(OwnerResource.PATH + "/" + createdOwner.getId())).body(createdOwner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> update(@PathVariable(value = "id") Integer id, @Valid @RequestBody OwnerRequest ownerRequest) {
        return ResponseEntity.ok(ownerService.update(id, ownerRequest));
    }

}
