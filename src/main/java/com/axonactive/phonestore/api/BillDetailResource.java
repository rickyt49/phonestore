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

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(BillDetailResource.PATH)
public class BillDetailResource {
    public static final String PATH = "/api/billdetails";

    @Autowired
    BillDetailService billDetailService;
    @Autowired
    BillDetailMapper billDetailMapper;

    @GetMapping
    public ResponseEntity<List<BillDetailDto>> getAll() {
        log.info("Getting all bill details");
        return ResponseEntity.ok(billDetailService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto> findBillDetailById(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        log.info("Getting bill detail with id: {}", id);
        return ResponseEntity.ok(billDetailService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BillDetailDto> add(@RequestBody BillDetailRequest billDetailRequest) throws EntityNotFoundException {
        BillDetailDto createdBillDetail = billDetailService.save(billDetailRequest);
        return ResponseEntity.created(URI.create(BillDetailResource.PATH + "/" + createdBillDetail.getId())).body(createdBillDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        billDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDetailDto> update(@PathVariable(value = "id") Integer id, @RequestBody BillDetailUpdateRequest billDetailUpdateRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(billDetailService.update(id, billDetailUpdateRequest));
    }
}
