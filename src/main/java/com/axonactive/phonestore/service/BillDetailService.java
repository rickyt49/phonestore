package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.exception.ResourceNotFoundException;
import com.axonactive.phonestore.service.dto.BillDetailDto;

import java.util.List;

public interface BillDetailService {
    List<BillDetailDto> getAll();

    BillDetailDto findById(Integer id) throws ResourceNotFoundException;

    BillDetailDto save(BillDetailRequest billDetail) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    BillDetailDto update(Integer id, BillDetailUpdateRequest billDetailUpdateRequest) throws ResourceNotFoundException;

    List<BillDetailDto> findByBillId(Integer id);

}
