package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.StoreRequest;
import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.*;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import com.axonactive.phonestore.service.dto.StoreDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import com.axonactive.phonestore.service.mapper.BillMapper;
import com.axonactive.phonestore.service.mapper.PhysicalPhoneMapper;
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
    private OwnerService ownerService;

    @Autowired
    private PhysicalPhoneService physicalPhoneService;

    @Autowired
    private PhysicalPhoneMapper physicalPhoneMapper;

    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private BillDetailService billDetailService;

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

    @GetMapping("/{id}/availablephones")
    public ResponseEntity<List<PhysicalPhoneDto>> getAllAvailablePhoneInStore(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(physicalPhoneMapper.toDtos(physicalPhoneService.findAllAvailablePhoneByStore(id)));
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<List<BillDto>> getAllBillByStore(@PathVariable("id") Integer id, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) throws ResourceNotFoundException {
        if (startDate == null && endDate == null) {
            List<Bill> bills = billService.findAllBillByStore(id);
            List<BillDto> billDtos = billMapper.toDtos(bills);
            for (BillDto billDto : billDtos) {
                billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(billDto.getId())));
            }
            return ResponseEntity.ok(billDtos);
        }
        if (startDate == null) {
            List<Bill> bills = billService.findByEmployeeIdAndSaleDateBefore(id, LocalDate.parse(endDate));
            List<BillDto> billDtos = billMapper.toDtos(bills);
            for (BillDto billDto : billDtos) {
                billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(billDto.getId())));
            }
            return ResponseEntity.ok(billDtos);
        }
        if (endDate == null) {
            List<Bill> bills = billService.findAllBillByStoreAndSaleDateAfter(id, LocalDate.parse(startDate));
            List<BillDto> billDtos = billMapper.toDtos(bills);
            for (BillDto billDto : billDtos) {
                billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(billDto.getId())));
            }
            return ResponseEntity.ok(billDtos);
        }
        List<Bill> bills = billService.findByEmployeeIdAndSaleDateBetween(id, LocalDate.parse(startDate), LocalDate.parse(endDate));
        List<BillDto> billDtos = billMapper.toDtos(bills);
        for (BillDto billDto : billDtos) {
            billDto.setBillDetailDtos(billDetailMapper.toDtos(billDetailService.findByBillId(billDto.getId())));
        }
        return ResponseEntity.ok(billDtos);
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
