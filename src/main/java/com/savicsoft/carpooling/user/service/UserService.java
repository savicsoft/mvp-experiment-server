package com.savicsoft.carpooling.user.service;

import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.model.form.UpdateUserForm;
import com.savicsoft.carpooling.user.model.form.UpdateUserPrefForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    boolean deleteUser(UUID id);

    boolean deleteProfilePicture(UUID id,String fileName);

    MultipartFile uploadProfilePicture(UUID id, MultipartFile picture);

    UserDTO updateUserInfo(UUID id, UpdateUserForm userForm);
    UUID getUserPreferenceId(UUID userId);

    UserPreferences updateUserPref(UUID id, UpdateUserPrefForm userPrefForm);

    UserDTO getUserByEmail(String email);

    MultipartFile downloadProfilePicture(UUID id);

    Boolean getHasPictureById(UUID id);

    boolean verify(String code);
}
