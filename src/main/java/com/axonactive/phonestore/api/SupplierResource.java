package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.SupplierRequest;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.SupplierService;
import com.axonactive.phonestore.service.dto.SupplierDto;
import com.axonactive.phonestore.service.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(SupplierResource.PATH)
public class SupplierResource {
    public static final String PATH = "/api/suppliers";

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierMapper supplierMapper;

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> findSupplierById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierDto> add(@RequestBody SupplierRequest supplierRequest) {
        SupplierDto createdSupplier = supplierService.save(supplierRequest);
        return ResponseEntity.created(URI.create(SupplierResource.PATH + "/" + createdSupplier.getId())).body(createdSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable(value = "id") Integer id, @RequestBody SupplierRequest supplierRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(supplierService.update(id, supplierRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SupplierDto>> findSupplierByPhoneNumber(@RequestParam(value = "phoneNumber", required = false) String phoneNumber, @RequestParam(value = "name", required = false) String name) {
        if (null == name && null != phoneNumber) {
            return ResponseEntity.ok(supplierService.findByPhoneNumberContaining(phoneNumber));
        }
        if (null == phoneNumber && null != name) {
            return ResponseEntity.ok(supplierService.findByFullNameContaining(name));
        }
        return ResponseEntity.ok(supplierService.getAll());
    }

}
