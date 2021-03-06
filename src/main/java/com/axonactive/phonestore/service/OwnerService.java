package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.OwnerRequest;
import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.OwnerDto;

import java.util.List;

public interface OwnerService {
    List<OwnerDto> getAll();

    OwnerDto findById(Integer id) ;

    OwnerDto save(OwnerRequest ownerRequest);

    void delete(Integer id);

    OwnerDto update(Integer id, OwnerRequest ownerRequest) ;

    List<Owner> findByPhoneNumberContaining(String phoneNumber);

    List<Owner> findByFullNameContaining(String name);
}
