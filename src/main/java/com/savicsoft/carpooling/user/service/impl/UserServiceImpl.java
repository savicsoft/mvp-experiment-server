package com.savicsoft.carpooling.user.service.impl;

import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Exception Handling not implemented yet!
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for(User user: users) {

            UserDTO userDTO = UserDTO.builder()
                            .id(user.getId())
                            .build();

            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public UserDTO getUserById(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){

            return null;

        }else {

            return UserDTO.fromUser(userOptional.get());

        }
    }

    @Override
    public UserDTO createUser(User user) {//Receive a RequestBody with a form?
        //Pending exception handling
        userRepository.save(user);

        return UserDTO.fromUser(user);
    }


    @Override
    public UserDTO updateUser(Long id) { //Receive parameter form or DTO instead Long?
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            return null;
        }

        User user = userRepository.save(userOptional.get());

        return UserDTO.fromUser(user);

    }

    @Override
    public UserDTO deleteUser(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            return null;
        }

        UserDTO userDTO = UserDTO.fromUser(userOptional.get());

        userRepository.deleteById(id);

        return userDTO;
    }
}
