package com.savicsoft.carpooling.user.service;

import com.savicsoft.carpooling.user.model.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.model.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(CreateUserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO deleteUser(Long id);
    UserDTO getUserByUuid(UUID uuid);

}
