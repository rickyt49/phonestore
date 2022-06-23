package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import com.axonactive.phonestore.service.mapper.BillDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(BillDetailResource.PATH)
public class BillDetailResource {
    public static final String PATH = "/api/billdetails";

    @Autowired
    BillDetailService billDetailService;

    @GetMapping
    public ResponseEntity<List<BillDetailDto>> getAll() {
        return ResponseEntity.ok(billDetailService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto> findBillDetailById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(billDetailService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BillDetailDto> add(@Valid @RequestBody BillDetailRequest billDetailRequest) {
        BillDetailDto createdBillDetail = billDetailService.save(billDetailRequest);
        return ResponseEntity.created(URI.create(BillDetailResource.PATH + "/" + createdBillDetail.getId())).body(createdBillDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
        billDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDetailDto> update(@PathVariable(value = "id") Integer id,@Valid @RequestBody BillDetailUpdateRequest billDetailUpdateRequest) {
        return ResponseEntity.ok(billDetailService.update(id, billDetailUpdateRequest));
    }
}
