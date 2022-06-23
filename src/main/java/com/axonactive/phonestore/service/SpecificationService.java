package com.axonactive.phonestore.service;

import com.axonactive.phonestore.api.request.SpecificationRequest;
import com.axonactive.phonestore.exception.EntityNotFoundException;
import com.axonactive.phonestore.service.dto.SpecificationDto;

import java.util.List;

public interface SpecificationService {
    List<SpecificationDto> getAll();

    SpecificationDto findById(Integer id) ;

    SpecificationDto save(SpecificationRequest specification);

    void delete(Integer id);

    SpecificationDto update(Integer id, SpecificationRequest specificationRequest) ;
}
