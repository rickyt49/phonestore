package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.OwnerService;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.StoreDto;
import com.axonactive.phonestore.service.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(StoreResource.PATH)
public class StoreResource {
    public static final String PATH = "/api/stores";
    @Autowired
    StoreService storeService;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAll() {
        return ResponseEntity.ok(storeMapper.toDtos(storeService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> findStoreById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(storeMapper.toDto(storeService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<StoreDto> add(@RequestBody StoreRequest storeRequest) throws ResourceNotFoundException {
        Store createdStore = storeService.save(new Store(null, storeRequest.getName(), storeRequest.getPhoneNumber(), storeRequest.getAddress(), storeRequest.getCity(), ownerService.findById(storeRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + storeRequest.getOwnerId()))));
        return ResponseEntity.created(URI.create(StoreResource.PATH + "/" + createdStore.getId())).body(storeMapper.toDto(createdStore));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(@PathVariable(value = "id") Integer id, @RequestBody StoreRequest storeRequest) throws ResourceNotFoundException {
        Store updatedStore = storeService.update(id, new Store(null, storeRequest.getName(), storeRequest.getPhoneNumber(), storeRequest.getAddress(), storeRequest.getCity(), ownerService.findById(storeRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + storeRequest.getOwnerId()))));
        return ResponseEntity.ok(storeMapper.toDto(updatedStore));
    }

}
