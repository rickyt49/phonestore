package com.axonactive.phonestore.service.mapper;

import com.axonactive.phonestore.entity.Customer;
import com.axonactive.phonestore.service.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
//    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toDto(Customer customer);

    List<CustomerDto> toDtos(List<Customer> customers);
}
