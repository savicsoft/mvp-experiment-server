package com.savicsoft.carpooling.user.model.mapper;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import com.savicsoft.carpooling.user.model.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "carsCount", expression = "java(mapCarsCount(user.getCars()))")
    @Mapping(target = "picture", ignore = true)
    UserDTO userToUserDTO(User user);

    List<UserDTO> usersToUserDTOs(List<User> users);

    default int mapCarsCount(Collection<Car> cars) {
        return cars != null ? cars.size() : 0;
    }

    @Mapping(target = "picture", source = "picture", qualifiedByName = "mapMultipartFileToBoolean")
    User userDTOToUser(UserDTO userDTO);

    @Named("mapMultipartFileToBoolean")
    static boolean mapMultipartFileToBoolean(MultipartFile picture) {
        return picture != null && !picture.isEmpty();
    }
}
