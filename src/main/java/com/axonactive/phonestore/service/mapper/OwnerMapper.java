package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Owner;
import com.axonactive.phonestore.service.dto.OwnerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    OwnerDto toDto(Owner owner);

    List<OwnerDto> toDtos(List<Owner> owners);

}
