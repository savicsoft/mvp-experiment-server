package com.savicsoft.carpooling.user.service;

import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(User user);
    UserDTO updateUser(Long id);
    UserDTO deleteUser(Long id);

}
