package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BillDetailMapper {
    BillDetailMapper INSTANCE = Mappers.getMapper(BillDetailMapper.class);

    @Mapping(source = "billDetail.physicalPhone.imei", target = "imei")
    @Mapping(source = "billDetail.physicalPhone.specification.model", target = "model")
    BillDetailDto toDto(BillDetail billDetail);

    List<BillDetailDto> toDtos(List<BillDetail> billDetails);
}
