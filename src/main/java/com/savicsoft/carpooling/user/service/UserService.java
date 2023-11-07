package com.savicsoft.carpooling.user.service;

import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
import com.savicsoft.carpooling.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(CreateUserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO deleteUser(Long id);

}
