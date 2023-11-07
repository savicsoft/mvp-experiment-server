package com.savicsoft.carpooling.user.controller;

import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.savicsoft.carpooling.user.model.entity.User;

//Exception Handling not implemented yet!
@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){

        List<UserDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        UserDTO userDTO = service.getUserById(id);

        return ResponseEntity.ok(userDTO);

    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO userDTO){ //Receive a RequestBody with Form?

        UserDTO createdUser = service.createUser(userDTO);

        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){ //Receive RequestBody with Form or DTO instead Long?

        UserDTO updatedUser = service.updateUser(userDTO);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){

        UserDTO deletedUser = service.deleteUser(id);

        return ResponseEntity.ok(deletedUser);
    }
}
