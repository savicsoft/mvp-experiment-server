package com.savicsoft.carpooling.user.service.impl;

import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.exception.UserCreationException;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
import com.savicsoft.carpooling.user.mapper.UserMapper;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return userMapper.usersToUserDTOList(users);
    }

    @Override
    public UserDTO getUserById(Long id){

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {

            throw new UserNotFoundException("User doesn't exist. Please, check the inserted ID");

        } else {

            return userMapper.userToUserDTO(userOptional.get());

        }
    }

    @Override
    public UserDTO createUser(CreateUserDTO userDTO) {//Receive a RequestBody with a form?

        User user = userMapper.createUserDTOToUser(userDTO);

        try{
            userRepository.save(user);

            return userMapper.userToUserDTO(user);

        }catch (DataAccessException dae){

            throw new UserCreationException("Internal Error. Couldn't create a new user.");

        }

    }


    @Override
    public UserDTO updateUser(Long id) { //Receive parameter form or DTO instead of Long?
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User doesn't exist. Please, check the inserted ID");
        }

        User user = userRepository.save(userOptional.get());

        return userMapper.userToUserDTO(userOptional.get());

    }

    @Override
    public UserDTO deleteUser(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User doesn't exist. Please, check the inserted ID");
        }

        userRepository.deleteById(id);

        return userMapper.userToUserDTO(userOptional.get());
    }
}
