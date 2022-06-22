package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.StoreService;
import com.axonactive.phonestore.service.dto.*;
import com.axonactive.phonestore.service.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
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
    public ResponseEntity<StoreDto> findStoreById(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StoreDto> add(@RequestBody StoreRequest storeRequest) throws EntityNotFoundException {
        StoreDto createdStore = storeService.save(storeRequest);
        return ResponseEntity.created(URI.create(StoreResource.PATH + "/" + createdStore.getId())).body(createdStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(@PathVariable(value = "id") Integer id, @RequestBody StoreRequest storeRequest) throws EntityNotFoundException {

        return ResponseEntity.ok(storeService.update(id, storeRequest));
    }

    @GetMapping("/{id}/availablephones")
    public ResponseEntity<List<PhysicalPhoneDto>> getAllAvailablePhoneInStore(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(physicalPhoneService.findAllAvailablePhoneByStore(id));
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillDto>> getAllBillByStore(@PathVariable("id") Integer id, @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return ResponseEntity.ok(billService.findAllBillByStore(id));
        }
        if (startDate == null) {
            return ResponseEntity.ok(billService.findAllBillByStoreAndSaleDateBefore(id, endDate));
        }
        if (endDate == null) {
            return ResponseEntity.ok(billService.findAllBillByStoreAndSaleDateAfter(id, startDate));
        }
        return ResponseEntity.ok(billService.findAllBillByStoreAndSaleDateBetween(id, startDate, endDate));
    }

    @GetMapping("/{id}/profit")
    public ResponseEntity<Integer> getGrossProfit(@PathVariable("id") Integer id, @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if ((startDate == null) && (endDate == null)) {
            return ResponseEntity.ok(billService.getTotalGrossProfit(id));
        }
        if (startDate == null) {
            return ResponseEntity.ok(billService.getTotalGrossProfitBefore(id, endDate));
        }
        if (endDate == null) {
            return ResponseEntity.ok(billService.getTotalGrossProfitAfter(id, startDate));
        }
        return ResponseEntity.ok(billService.getTotalGrossProfitBetween(id, startDate, endDate));
    }

    @GetMapping("/{id}/stockreport")
    public List<PhoneModelAndAmountDto> getStockReportByStore(@PathVariable("id") Integer storeId) {
        return storeService.getPhoneModelAndItsAmountByStoreId(storeId);
    }

    @GetMapping("/{id}/customerreport")
    public List<CustomerAndTotalPurchaseDto> getCustomerPurchaseReportByStore(@PathVariable("id") Integer storeId) {
        return storeService.getCustomerTotalPurchaseReportByStoreId(storeId);
    }


}
