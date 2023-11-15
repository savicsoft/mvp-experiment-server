package com.savicsoft.carpooling.user.service.impl;

import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.exception.CouldNotCreateUserException;
import com.savicsoft.carpooling.user.exception.CouldNotUpdateUserException;
import com.savicsoft.carpooling.user.exception.UserDataAccessException;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
import com.savicsoft.carpooling.user.exception.CouldNotDeleteUserException;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.usersToUserDTOList(users);
        } catch (DataAccessException e) {
            throw new UserDataAccessException("Internal Error. Could not access user registers");
        }

    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User doesn't exist for ID: " + id + ". Please, check the entered input");
        } else {
            return userMapper.userToUserDTO(userOptional.get());
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User doesn't exist for username: " + email + ". Please, check the entered input");
        } else {
            return userMapper.userToUserDTO(userOptional.get());
        }
    }

    @Override
    public UserDTO createUser(CreateUserDTO userDTO) {
        User user = userMapper.createUserDTOToUser(userDTO);
        try {
            userRepository.save(user);
            return userMapper.userToUserDTO(user);
        } catch (DataAccessException dae) {
            throw new CouldNotCreateUserException("Internal Error. Could not create a new user.");
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) { //Receive parameter form or DTO instead of Long?
        Long id = userDTO.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User doesn't exist for ID: " + id + ". Please, check the entered input");
        }
        try {
            User user = userRepository.save(userOptional.get());
            return userMapper.userToUserDTO(user);
        } catch (DataAccessException e) {
            throw new CouldNotUpdateUserException("Internal Error. Could not apply the requested changes");
        }
    }

    @Override
    public UserDTO deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist for ID: " + id + ". Please, check the entered input");
        }
        try {
            userRepository.deleteById(id);
            return userMapper.userToUserDTO(userOptional.get());
        } catch (DataAccessException e) {
            throw new CouldNotDeleteUserException("Internal Error. Could not delete the user register");
        }
    }

    @Override
    public UserDTO getUserByUuid(UUID uuid) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        if (userOptional.isEmpty()) {
            return null;
        } else {
            return userMapper.userToUserDTO(userOptional.get());
        }
    }
}
