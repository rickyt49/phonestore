package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.BillDetailRequest;
import com.axonactive.phonestore.api.request.BillDetailUpdateRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.BillDetailDto;

import java.util.List;

public interface BillDetailService {
    List<BillDetailDto> getAll();

    BillDetailDto findById(Integer id) throws EntityNotFoundException;

    BillDetailDto save(BillDetailRequest billDetail) throws EntityNotFoundException;

    void delete(Integer id) throws EntityNotFoundException;

    BillDetailDto update(Integer id, BillDetailUpdateRequest billDetailUpdateRequest) throws EntityNotFoundException;

    List<BillDetailDto> findByBillId(Integer id);

}
