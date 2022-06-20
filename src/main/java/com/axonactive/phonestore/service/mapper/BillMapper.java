package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.entity.BillDetail;
import com.axonactive.phonestore.service.dto.BillDetailDto;
import com.axonactive.phonestore.service.dto.BillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface    BillMapper {
//    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    @Mapping(target = "employeeFullName", expression = "java(bill.getEmployee().getFirstName() + \" \" + bill.getEmployee().getLastName())")
    @Mapping(target = "customerName", source = "bill.customer.fullName")
    BillDto toDto(Bill bill);
    List<BillDto> toDtos(List<Bill> bills);

    @Mapping(source = "billDetail.physicalPhone.imei", target = "imei")
    @Mapping(source = "billDetail.physicalPhone.specification.model", target = "model")
    BillDetailDto toBillDetailDto(BillDetail billDetail);
    List<BillDetailDto> toBillDetailDtos(List<BillDetail> billDetails);
}
