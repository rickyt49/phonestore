package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.PhysicalPhoneService;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(BillDetailResource.PATH)
public class BillDetailResource {
    public static final String PATH = "/api/billdetails";

    @Autowired
    BillDetailService billDetailService;
    @Autowired
    BillDetailMapper billDetailMapper;
    @Autowired
    BillService billService;
    @Autowired
    PhysicalPhoneService physicalPhoneService;

    @GetMapping
    public ResponseEntity<List<BillDetailDto>> getAll() {
        return ResponseEntity.ok(billDetailMapper.toDtos(billDetailService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto> findBillDetailById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(billDetailMapper.toDto(billDetailService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill Detail not found " + id))));
    }

    @PostMapping
    public ResponseEntity<BillDetailDto> add(@RequestBody BillDetailRequest billDetailRequest) throws ResourceNotFoundException {
        BillDetail billDetail = new BillDetail();
        billDetail.setBill(billService.findById(billDetailRequest.getBillId()).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + billDetailRequest.getBillId())));
        billDetail.setPhysicalPhone(physicalPhoneService.findById(billDetailRequest.getPhoneId()).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + billDetailRequest.getPhoneId())));
        billDetail.setSellPrice(billDetailRequest.getSellPrice());
        billDetail.setDiscountAmount(billDetailRequest.getDiscountAmount());
        billDetail.setFinalSellPrice(billDetailRequest.getSellPrice() - billDetailRequest.getDiscountAmount());
        BillDetail createdBillDetail = billDetailService.save(billDetail);
        return ResponseEntity.created(URI.create(BillDetailResource.PATH + "/" + createdBillDetail.getId())).body(billDetailMapper.toDto(createdBillDetail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        billDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDetailDto> update(@PathVariable(value = "id") Integer id, @RequestBody BillDetailRequest billDetailRequest) throws ResourceNotFoundException {
        BillDetail updatedBillDetail = new BillDetail();
        updatedBillDetail.setBill(billService.findById(billDetailRequest.getBillId()).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + billDetailRequest.getBillId())));
        updatedBillDetail.setPhysicalPhone(physicalPhoneService.findById(billDetailRequest.getPhoneId()).orElseThrow(() -> new ResourceNotFoundException("Phone not found: " + billDetailRequest.getPhoneId())));
        updatedBillDetail.setSellPrice(billDetailRequest.getSellPrice());
        updatedBillDetail.setDiscountAmount(billDetailRequest.getDiscountAmount());
        updatedBillDetail.setFinalSellPrice(billDetailRequest.getSellPrice() - billDetailRequest.getDiscountAmount());
        return ResponseEntity.ok(billDetailMapper.toDto(billDetailService.update(id, updatedBillDetail)));
    }
}
