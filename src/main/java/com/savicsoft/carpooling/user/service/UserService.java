package com.savicsoft.carpooling.user.service;

import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
import com.savicsoft.carpooling.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id) throws UserNotFoundException;
    UserDTO createUser(CreateUserDTO userDTO);
    UserDTO updateUser(Long id) throws UserNotFoundException;
    UserDTO deleteUser(Long id) throws UserNotFoundException;

}
