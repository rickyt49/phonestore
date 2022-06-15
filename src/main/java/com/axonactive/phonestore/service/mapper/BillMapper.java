package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Bill;
import com.axonactive.phonestore.service.BillDetailService;
import com.axonactive.phonestore.service.dto.BillDto;
import org.aspectj.lang.annotation.After;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {
//    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);


    @Mapping(target = "employeeFullName", expression = "java(bill.getEmployee().getFirstName() + \" \" + bill.getEmployee().getLastName())")
    @Mapping(target = "customerName", source = "bill.customer.fullName")
    BillDto toDto(Bill bill);

    List<BillDto> toDtos(List<Bill> bills);

}
