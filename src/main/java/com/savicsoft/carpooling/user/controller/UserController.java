package com.savicsoft.carpooling.user.controller;

import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.savicsoft.carpooling.user.model.entity.User;

//Exception Handling not implemented yet!
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){

        List<UserDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        UserDTO userDTO = service.getUserById(id);

        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){ //Receive a RequestBody with Form?

        UserDTO createdUser = service.createUser(user);

        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id){ //Receive RequestBody with Form or DTO instead Long?

        UserDTO updatedUser = service.updateUser(id);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){

        UserDTO deletedUser = service.deleteUser(id);

        return ResponseEntity.ok(deletedUser);
    }

}
