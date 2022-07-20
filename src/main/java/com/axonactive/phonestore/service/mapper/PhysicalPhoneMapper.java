package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.PhysicalPhone;
import com.axonactive.phonestore.service.dto.PhysicalPhoneDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhysicalPhoneMapper {
//    PhysicalPhoneMapper INSTANCE = Mappers.getMapper(PhysicalPhoneMapper.class);

    @Mapping(source = "physicalPhone.specification.model", target = "specificationModel")
    @Mapping(target = "memorySize", expression = "java(Integer.toString(physicalPhone.getMemorySize())+ \" GB\")")
    @Mapping(source = "physicalPhone.store.id", target = "storeId")
    @Mapping(source = "physicalPhone.supplier.id", target = "supplierId")
    PhysicalPhoneDto toDto(PhysicalPhone physicalPhone);


    List<PhysicalPhoneDto> toDtos(List<PhysicalPhone> physicalPhones);
}
