package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Supplier;
import com.axonactive.phonestore.service.dto.SupplierDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
//    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDto toDto(Supplier supplier);

    List<SupplierDto> toDtos(List<Supplier> suppliers);
}
