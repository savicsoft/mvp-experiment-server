package com.savicsoft.carpooling.user.mapper;

import com.savicsoft.carpooling.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.savicsoft.carpooling.user.model.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToDTO (User user);
    @Mapping(target = "password", ignore = true)
    User userDTOToUser (UserDTO dto);
    List<UserDTO> usersToUserDTOList(List<User> users);
    List<User> userDTOsToUserList(List<UserDTO> users);

}
