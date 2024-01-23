package com.savicsoft.carpooling.user.controller;

import com.savicsoft.carpooling.exception.CouldNotDeleteException;
import com.savicsoft.carpooling.exception.errorinfo.ErrorInfo;
import com.savicsoft.carpooling.security.auth.AuthUtil;
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

@RequestMapping("/api/v2/users")
@RestController
@RequiredArgsConstructor
@Tag(
        name = "User resource",
        description = "Operations to interact with user info"
)
public class AuthenticatedUserController {
    private final UserService userService;
    private final AuthUtil authUtil;
    @Operation(
            summary = "Receive user information",
            description = "Retrieve authenticated user"
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
    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUserById(){
        UUID id = authUtil.getIdFromContext();
        UserDTO userDTO = userService.getUserById(id);
        if(userService.getHasPictureById(id)){
            MultipartFile picture = userService.downloadProfilePicture(id);
            userDTO.setPicture(picture);
        }
        return ResponseEntity.ok(userDTO);
    }
    @Operation(
            summary = "Delete user",
            description = "Delete authenticated user"
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
    @DeleteMapping()
    public ResponseEntity<Boolean> deleteUser(){
        UUID id = authUtil.getIdFromContext();
        Boolean deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }
    @Operation(
            summary = "Upload user profile picture",
            description = "Provide authenticated user file to upload profile picture to the storage"
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
                            description = "HTTP Status: 404 -> User with id not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not upload the picture",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultipartFile> uploadProfilePicture(@RequestParam MultipartFile picture) {
        UUID id = authUtil.getIdFromContext();
        MultipartFile uploadedFile = userService.uploadProfilePicture(id, picture);
        return ResponseEntity.ok(uploadedFile);
    }
    @Operation(
            summary = "Delete user profile picture",
            description = "Provide authenticated user filename to delete profile picture from the storage"
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
    @DeleteMapping("/picture")
    public ResponseEntity<Boolean> deleteProfilePicture(@RequestParam String filename){
        String[] parts = filename.split("/");
        String idFile = parts[0];
        UUID id = authUtil.getIdFromContext();
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
    @PatchMapping()
    public ResponseEntity<UserDTO> updateUserInfo(UpdateUserForm userForm){
        UUID id = authUtil.getIdFromContext();
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
    @PatchMapping("/preferences")
    public ResponseEntity<UserPreferences> updateUserPreferences(UpdateUserPrefForm userPrefForm){
        UUID userId = authUtil.getIdFromContext();
        UUID prefId = userService.getUserPreferenceId(userId);
        UserPreferences updatedPreferences = userService.updateUserPref(prefId,userPrefForm);
        return ResponseEntity.ok(updatedPreferences);
    }

}
