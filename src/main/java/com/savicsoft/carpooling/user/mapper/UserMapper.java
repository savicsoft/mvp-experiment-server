package com.savicsoft.carpooling.user.mapper;

import com.savicsoft.carpooling.user.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.savicsoft.carpooling.user.model.entity.User;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    @Mapping(target = "password", ignore = true)
    User userDTOToUser (UserDTO dto);
    CreateUserDTO userToCreateUserDTO(User user);
    User createUserDTOToUser(CreateUserDTO dto);
    List<UserDTO> usersToUserDTOList(List<User> users);
    List<User> userDTOsToUserList(List<UserDTO> users);

}
