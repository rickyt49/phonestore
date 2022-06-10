package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.EmployeeAccount;
import com.axonactive.phonestore.service.dto.EmployeeAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeAccountMapper {
//    EmployeeAccountMapper INSTANCE = Mappers.getMapper(EmployeeAccountMapper.class);

    @Mapping(source = "employee.id", target = "employeeId")
    EmployeeAccountDto toDto(EmployeeAccount employeeAccount);

    List<EmployeeAccountDto> toDtos(List<EmployeeAccount> employeeAccounts);
}
