package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.service.dto.StoreDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
//    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);



    StoreDto toDto(Store store);

    List<StoreDto> toDtos(List<Store> stores);

}
