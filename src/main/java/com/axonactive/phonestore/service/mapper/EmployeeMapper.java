package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Employee;
import com.axonactive.phonestore.service.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
//    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "fullName", expression = "java(employee.getFirstName() + \" \" + employee.getLastName())")
    @Mapping(source = "employee.store.name",target = "storeName")
    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toDtos(List<Employee> employees);
}
