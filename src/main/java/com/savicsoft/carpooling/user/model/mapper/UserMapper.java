package com.savicsoft.carpooling.user.model.mapper;

import com.savicsoft.carpooling.user.model.dto.CreateUserDTO;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import com.savicsoft.carpooling.user.model.entity.User;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //@Mapping(target = "car", ignore = true)
    UserDTO userToUserDTO(User user);
    /*@Mapping(target = "password", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "role", ignore = true)*/
    User userDTOToUser (UserDTO dto);
    CreateUserDTO userToCreateUserDTO(User user);
    User createUserDTOToUser(CreateUserDTO dto);
    List<UserDTO> usersToUserDTOList(List<User> users);
    List<User> userDTOsToUserList(List<UserDTO> users);

}
