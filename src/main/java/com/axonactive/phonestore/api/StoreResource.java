package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.dto.StoreDto;
import com.axonactive.phonestore.service.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(StoreResource.PATH)
public class StoreResource {
    public static final String PATH = "/api/stores";
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private PhysicalPhoneService physicalPhoneService;

    @Autowired
    private BillService billService;

    @GetMapping

    public ResponseEntity<List<StoreDto>> getAll() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> findStoreById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StoreDto> add(@RequestBody StoreRequest storeRequest) throws ResourceNotFoundException {
        StoreDto createdStore = storeService.save(storeRequest);
        return ResponseEntity.created(URI.create(StoreResource.PATH + "/" + createdStore.getId())).body(createdStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(@PathVariable(value = "id") Integer id, @RequestBody StoreRequest storeRequest) throws ResourceNotFoundException {

        return ResponseEntity.ok(storeService.update(id, storeRequest));
    }

    @GetMapping("/{id}/availablephones")
    public ResponseEntity<List<PhysicalPhoneDto>> getAllAvailablePhoneInStore(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(physicalPhoneService.findAllAvailablePhoneByStore(id));
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillDto>> getAllBillByStore(@PathVariable("id") Integer id, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) throws ResourceNotFoundException {
        if (startDate == null && endDate == null) {
            return ResponseEntity.ok(billService.findAllBillByStore(id));
        }
        if (startDate == null) {
            return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBefore(id, LocalDate.parse(endDate)));
        }
        if (endDate == null) {
            return ResponseEntity.ok(billService.findAllBillByStoreAndSaleDateAfter(id, LocalDate.parse(startDate)));
        }

        return ResponseEntity.ok(billService.findByEmployeeIdAndSaleDateBetween(id, LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping("/{id}/profit")
    public Integer getGrossProfit(@PathVariable("id") Integer id, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) {
        if (startDate == null && endDate == null) {
            return billService.getTotalGrossProfit(id);
        }
        if (startDate == null) {
            return billService.getTotalGrossProfitBefore(id, LocalDate.parse(endDate));
        }
        if (endDate == null) {
            return billService.getTotalGrossProfitAfter(id, LocalDate.parse(startDate));
        }
        return billService.getTotalGrossProfitBetween(id, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
