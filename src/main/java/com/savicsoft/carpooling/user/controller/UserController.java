package com.savicsoft.carpooling.user.controller;

import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.google.cloud.storage.Blob;
import com.savicsoft.carpooling.domain.HttpResponse;
import com.savicsoft.carpooling.domain.ImageUploadForm;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.user.dto.UserDTO;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final GoogleCloudStorageService storageService;

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

    @PostMapping("/{userUUID}/upload-profile-pic")
    public ResponseEntity<HttpResponse> uploadProfilePicture(@PathVariable UUID userUUID, @RequestBody ImageUploadForm userImageForm) {

        if (service.getUserByUuid(userUUID).getPictureUrl() != null) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("error", "Profile picture already exists."))
                            .message("Profile picture already exists")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        try {
            String fullFileName = userUUID.toString() + "/" + userImageForm.getFileName();

            Blob uploadedPicture = storageService.uploadFile(userImageForm.getPicture(), fullFileName, userUUID);
            service.getUserByUuid(userUUID).setPictureUrl("https://storage.googleapis.com/" + uploadedPicture.getName());

            return ResponseEntity.ok(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("picture", uploadedPicture))
                            .message("Profile picture uploaded successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("error", "Failed to upload profile picture: " + e.getMessage()))
                                    .message("Failed to upload profile picture")
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .build()
                    );
        }
    }
}
