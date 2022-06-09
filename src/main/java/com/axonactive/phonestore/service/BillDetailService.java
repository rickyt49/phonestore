package com.axonactive.phonestore.service;

import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BillDetailService {
    List<BillDetail> getAll();

    Optional<BillDetail> findById(Integer id);

    BillDetail save(BillDetail billDetail);

    void delete(Integer id);

    BillDetail update(Integer id, BillDetail billDetailDetails) throws ResourceNotFoundException;
}
