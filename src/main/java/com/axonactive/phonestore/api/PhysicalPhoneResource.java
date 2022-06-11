package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.PhysicalPhoneRequest;
import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.SpecificationService;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.SupplierService;
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

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private SpecificationService specificationService;

    @GetMapping
    public ResponseEntity<List<PhysicalPhoneDto>> getAll() {
        return ResponseEntity.ok(physicalPhoneMapper.toDtos(physicalPhoneService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> findPhoneById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(physicalPhoneMapper.toDto(physicalPhoneService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<PhysicalPhoneDto> add(@RequestBody PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException {
        PhysicalPhone addedPhone = physicalPhoneService.save(new PhysicalPhone(
                null,
                physicalPhoneRequest.getImei(),
                physicalPhoneRequest.getColor(),
                physicalPhoneRequest.getMemorySize(),
                physicalPhoneRequest.getPhoneStatus(),
                physicalPhoneRequest.getCondition(),
                physicalPhoneRequest.getWarranty(),
                physicalPhoneRequest.getImportPrice(),
                physicalPhoneRequest.getImportDate(),
                storeService.findById(physicalPhoneRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + physicalPhoneRequest.getStoreId())),
                supplierService.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + physicalPhoneRequest.getSupplierId())),
                specificationService.findById(physicalPhoneRequest.getSpecificationId()).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + physicalPhoneRequest.getSpecificationId()))
        ));
        return ResponseEntity.created(URI.create(PhysicalPhoneResource.PATH + "/" + addedPhone.getId())).body(physicalPhoneMapper.toDto(addedPhone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        physicalPhoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhysicalPhoneDto> update(@PathVariable(value = "id") Integer id, @RequestBody PhysicalPhoneRequest physicalPhoneRequest) throws ResourceNotFoundException {
        PhysicalPhone updatedPhone = physicalPhoneService.update(id, new PhysicalPhone(
                null,
                physicalPhoneRequest.getImei(),
                physicalPhoneRequest.getColor(),
                physicalPhoneRequest.getMemorySize(),
                physicalPhoneRequest.getPhoneStatus(),
                physicalPhoneRequest.getCondition(),
                physicalPhoneRequest.getWarranty(),
                physicalPhoneRequest.getImportPrice(),
                physicalPhoneRequest.getImportDate(),
                storeService.findById(physicalPhoneRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + physicalPhoneRequest.getStoreId())),
                supplierService.findById(physicalPhoneRequest.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + physicalPhoneRequest.getSupplierId())),
                specificationService.findById(physicalPhoneRequest.getSpecificationId()).orElseThrow(() -> new ResourceNotFoundException("Specification not found: " + physicalPhoneRequest.getSpecificationId()))
        ));
        return ResponseEntity.ok(physicalPhoneMapper.toDto(updatedPhone));
    }
}
