package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Specification;
import com.axonactive.phonestore.service.dto.SpecificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecificationMapper {
//    SpecificationMapper INSTANCE = Mappers.getMapper(SpecificationMapper.class);

    @Mapping(target = "ram", expression = "java( Integer.toString(specification.getRam()) + \" GB\")")

    @Mapping(target = "batteryCapacity", expression = "java(Integer.toString(specification.getBatteryCapacity())+ \" mAh\")")

    @Mapping(target = "screen", expression = "java(specification.getScreenType() + \", \" +specification.getScreenResolution())")
    SpecificationDto toDto(Specification specification);

    List<SpecificationDto> toDtos(List<Specification> specifications);
}
