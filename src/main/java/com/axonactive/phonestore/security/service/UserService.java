package com.axonactive.phonestore.security.service;

import com.axonactive.phonestore.security.service.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

}
