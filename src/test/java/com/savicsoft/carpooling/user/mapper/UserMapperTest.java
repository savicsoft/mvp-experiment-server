package com.savicsoft.carpooling.user.mapper;

import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.savicsoft.carpooling.user.model.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {

    private UserMapper userMapper;
    private User user1;
    private User user2;
    private UserDTO userDTO1;
    List<User> users = new ArrayList<>();
    List<UserDTO> userDTOs = new ArrayList<>();
    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        user1 = User.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .email("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.of(1990,1,1))
                .country("USA")
                .city("New York")
                .driver(true)
                .build();

        userDTO1 = userMapper.userToUserDTO(user1);
    }

    @Test
    void userToUserDTO() {

        UserDTO userDTO = userMapper.userToUserDTO(user1);
        assertThat(user1)
                .usingRecursiveComparison()
                .ignoringFields("password","cars")
                .isEqualTo(userDTO);

    }

    @Test
    void userDTOToUser() {
        user2 = userMapper.userDTOToUser(userDTO1);
        assertThat(user1)
                .usingRecursiveComparison()
                .ignoringFields("password","cars")
                .isEqualTo(userDTO1);

    }

    @Test
    void usersToUserDTOList() {
        users.add(user1);
        users.add(user2);
        userDTOs = userMapper.usersToUserDTOList(users);

        assertThat(users)
                .usingRecursiveComparison()
                .ignoringFields("password","cars")
                .isEqualTo(userDTOs);


    }

    @Test
    void userDTOsToUserList() {
        userMapper.userDTOsToUserList(userDTOs);
        assertThat(users)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(userDTOs);
    }
}