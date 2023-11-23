package com.savicsoft.carpooling.user.controller;

import com.savicsoft.carpooling.exception.CouldNotDeleteException;
import com.savicsoft.carpooling.exception.errorinfo.ErrorInfo;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.model.form.UpdateUserForm;
import com.savicsoft.carpooling.user.model.form.UpdateUserPrefForm;
import com.savicsoft.carpooling.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
@Tag(
        name = "REST APIs for user",
        description = "Operations to interact with user info"
)


// Security needs to be added later
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Receive user information",
            description = "Retrieve user by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status: 400 -> User picture cannot be retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id){
        UserDTO userDTO = userService.getUserById(id);
        if(userService.getHasPictureById(id)){
            MultipartFile picture = userService.downloadProfilePicture(id);
            userDTO.setPicture(picture);
        }
        return ResponseEntity.ok(userDTO);
    }


    @Operation(
            summary = "Delete user",
            description = "Delete user by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not delete the user",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id){
        Boolean deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }


    @Operation(
            summary = "Upload user profile picture",
            description = "Provide user id and file to upload profile picture to the storage"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipartFile.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status: 400 -> Invalid data received",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> User with provided id not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not upload the picture",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PostMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultipartFile> uploadProfilePicture(@PathVariable UUID id, @RequestParam MultipartFile picture) {
        MultipartFile uploadedFile = userService.uploadProfilePicture(id, picture);
        return ResponseEntity.ok(uploadedFile);
    }


    @Operation(
            summary = "Delete user profile picture",
            description = "Provide user id and filename to delete profile picture from the storage"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status: 400 -> Invalid data received",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> File not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not delete the picture",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @DeleteMapping("/{id}/picture")
    public ResponseEntity<Boolean> deleteProfilePicture(@PathVariable UUID id, @RequestParam String filename){
        String[] parts = filename.split("/");
        String idFile = parts[0];
        if(!id.toString().equals(idFile)){
            throw new CouldNotDeleteException("User doesn't have access to delete picture " + filename);
        }
        Boolean deleted = userService.deleteProfilePicture(id, filename);
        return ResponseEntity.ok(deleted);
    }

    @Operation(
            summary = "Update user information",
            description = "Provide information specified in user form to update user info"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not update the user",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserInfo(@PathVariable UUID id, UpdateUserForm userForm){
        UserDTO updatedUser = userService.updateUserInfo(id, userForm);
        return ResponseEntity.ok(updatedUser);
    }


    @Operation(
            summary = "Update user preferences information",
            description = "Provide information specified in user preferences form to update user preferences"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPreferences.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> User preferences not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not update the user preferences",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PatchMapping("/preferences/{prefId}")
    public ResponseEntity<UserPreferences> updateUserPreferences(@PathVariable UUID prefId, UpdateUserPrefForm userPrefForm){
            UserPreferences updatedPreferences = userService.updateUserPref(prefId,userPrefForm);
            return ResponseEntity.ok(updatedPreferences);
    }


}
