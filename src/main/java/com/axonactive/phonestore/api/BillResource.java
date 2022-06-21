package com.axonactive.phonestore.api;

import com.axonactive.phonestore.api.request.BillRequest;
import com.axonactive.phonestore.exception.BusinessLogicException;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.BillService;
import com.axonactive.phonestore.service.dto.BillDto;
import com.axonactive.phonestore.service.mapper.BillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(BillResource.PATH)
public class BillResource {
    public static final String PATH = "/api/bills";
    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;


    @GetMapping
    public ResponseEntity<List<BillDto>> getAll() {
        return ResponseEntity.ok(billService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> findBillById(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok(billService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BillDto> add(@RequestBody BillRequest billRequest) throws EntityNotFoundException {
        BillDto createdBill = billService.save(billRequest);
        return ResponseEntity.created(URI.create(BillResource.PATH + "/" + createdBill.getId())).body(createdBill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> update(@PathVariable(value = "id") Integer id, @RequestBody BillRequest billRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(billService.update(id, billRequest));
    }
}
