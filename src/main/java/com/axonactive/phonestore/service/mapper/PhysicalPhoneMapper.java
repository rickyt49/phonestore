package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PhysicalPhoneMapper {
    PhysicalPhoneMapper INSTANCE = Mappers.getMapper(PhysicalPhoneMapper.class);

    @Mapping(source = "physicalPhone.specification.model", target = "model")
    PhysicalPhoneDto toDto(PhysicalPhone physicalPhone);

    List<PhysicalPhoneDto> toDtos(List<PhysicalPhone> physicalPhones);
}
