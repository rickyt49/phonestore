package com.axonactive.phonestore.security.service.impl;

import com.axonactive.phonestore.security.repository.UserRepository;
import com.axonactive.phonestore.security.service.UserService;
import com.axonactive.phonestore.security.service.dto.UserDTO;
import com.axonactive.phonestore.security.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<UserDTO> getUsers() {
                return UserMapper.INSTANCE.mapToDtos(userRepository.findAll());

    }
}
