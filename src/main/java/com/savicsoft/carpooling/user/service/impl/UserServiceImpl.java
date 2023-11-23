package com.savicsoft.carpooling.user.service.impl;

import com.savicsoft.carpooling.exception.CouldNotDeleteException;
import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.model.form.UpdateUserForm;
import com.savicsoft.carpooling.user.model.form.UpdateUserPrefForm;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserPreferencesRepository;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserPreferencesRepository userPreferencesRepository;
    private final UserMapper userMapper;

    private final GoogleCloudStorageService googleCloudStorageService;

    @Override
    public UserDTO getUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new NotFoundException("User with UUID: " + id + " does not exist.");
        return userMapper.userToUserDTO(userOptional.get());
    }

    @Override
    public MultipartFile uploadProfilePicture(UUID id, MultipartFile picture) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw new NotFoundException("User with UUID: " + id + " does not exist.");
        String fullFileName = id + "/" + picture.getName();
        googleCloudStorageService.uploadFile(picture, fullFileName, id);
        userOptional.get().setPicture(true);
        return picture;
    }

    @Override
    public boolean deleteUser(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with UUID: " + id + " does not exist.");
        }
        try {
            userRepository.deleteById(id);
            return userRepository.findById(userOptional.get().getId()).isEmpty();
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not delete the user");
        }

    }

    @Override
    public UserDTO updateUserInfo(UUID id, UpdateUserForm userForm) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with UUID: " + id + " does not exist.");
        }
        try {
            User user = userOptional.get();
            user.setPhoneNumber(Optional.ofNullable(userForm.getPhoneNumber()).orElse(user.getPhoneNumber()));
            user.setFirstName(Optional.ofNullable(userForm.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(userForm.getLastName()).orElse(user.getLastName()));
            user.setBirthDate(Optional.ofNullable(userForm.getBirthDate()).orElse(user.getBirthDate()));
            user.setCountry(Optional.ofNullable(userForm.getCountry()).orElse(user.getCountry()));
            user.setCity(Optional.ofNullable(userForm.getCity()).orElse(user.getCity()));
            user.setDriver(Optional.ofNullable(userForm.isDriver()).orElse(user.isDriver()));
            userRepository.save(user);
            return UserMapper.INSTANCE.userToUserDTO(user);
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not update the user");
        }
    }
    @Override
    public UserPreferences updateUserPref(UUID id, UpdateUserPrefForm userPrefForm) {
        Optional<UserPreferences> userPreferencesOptional = userPreferencesRepository.findById(id);
        if(userPreferencesOptional.isEmpty()){
            throw new NotFoundException("User preferences with UUID: " + id + " does not exist.");
        }
        try {
            UserPreferences userPreferences = userPreferencesOptional.get();
            userPreferences.setLanguage(Optional.ofNullable(userPrefForm.getLanguage()).orElse(userPreferences.getLanguage()));
            userPreferences.setCommunication(Optional.ofNullable(userPrefForm.getCommunication()).orElse(userPreferences.getCommunication()));
            userPreferences.setSmoking(Optional.ofNullable(userPrefForm.getSmoking()).orElse(userPreferences.getSmoking()));
            userPreferences.setMusic(Optional.ofNullable(userPrefForm.getMusic()).orElse(userPreferences.getMusic()));
            userPreferencesRepository.save(userPreferences);
            return userPreferences;
        }catch (DataAccessException e){
            throw new CouldNotDeleteException("Internal Error. Could not update the user preferences");
        }


    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.getUserByEmail(email);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User with email: " + email + " does not exist.");
        }
        return  UserMapper.INSTANCE.userToUserDTO(userOptional.get());
    }

    @Override
    public MultipartFile downloadProfilePicture(UUID id) {
        List<MultipartFile> picture = googleCloudStorageService.downloadFiles(id.toString());
        return picture.get(0);
    }

    @Override
    public Boolean getHasPictureById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().isPicture();
    }

    @Override
    public boolean deleteProfilePicture(UUID id, String fileName) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User with UUID: " + id + " does not exist.");
        }
        boolean deleted = googleCloudStorageService.deleteFile(fileName);
        if(deleted){
                userOptional.get().setPicture(false);
                userRepository.save(userOptional.get());
        }
        return deleted;
    }
}
