package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.SupplierRequest;
import com.axonactive.phonestore.entity.Supplier;
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
        return ResponseEntity.ok(supplierMapper.toDtos(supplierService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> findSupplierById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(supplierMapper.toDto(supplierService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<SupplierDto> add(@RequestBody SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setFullName(supplierRequest.getFullName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setPhoneNumber(supplierRequest.getPhoneNumber());
        Supplier createdSupplier = supplierService.save(supplier);
        return ResponseEntity.created(URI.create(SupplierResource.PATH + "/" + createdSupplier.getId())).body(supplierMapper.toDto(createdSupplier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable(value = "id") Integer id, @RequestBody SupplierRequest supplierRequest) throws ResourceNotFoundException {
        Supplier supplier = new Supplier();
        supplier.setFullName(supplierRequest.getFullName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setPhoneNumber(supplierRequest.getPhoneNumber());
        return ResponseEntity.ok(supplierMapper.toDto(supplierService.update(id, supplier)));
    }

//    @GetMapping()
//    public ResponseEntity<List<SupplierDto>> findSupplierByPhoneNumber(@RequestParam(value = "phonenumber") String phoneNumber) {
//        return ResponseEntity.ok(supplierMapper.toDtos(supplierService.findByPhoneNumberContaining(phoneNumber)));
//    }
//
//    @GetMapping("/where")
//    public ResponseEntity<List<SupplierDto>> findSupplierByFullName(@RequestParam("name") String name) {
//        return ResponseEntity.ok(supplierMapper.toDtos(supplierService.findByFullNameContaining(name)));
//    }
}
