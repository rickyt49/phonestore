package com.axonactive.phonestore.security.service.mapper;

import com.axonactive.phonestore.security.entity.User;
import com.axonactive.phonestore.security.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO mapToDto(User user);

    List<UserDTO> mapToDtos(List<User> users);

}
