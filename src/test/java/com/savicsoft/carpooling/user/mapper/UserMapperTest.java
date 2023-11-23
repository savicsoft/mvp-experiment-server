package com.savicsoft.carpooling.user.mapper;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.savicsoft.carpooling.user.model.entity.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application.properties")
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testUserToUserDTO() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@gmail.com");
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setBirthDate(LocalDate.of(1996, 3, 20));
        user.setCountry("Germany");
        user.setCity("Berlin");
        user.setDriver(true);


        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setLanguage("English");
        userPreferences.setMusic("Rap");
        userPreferences.setSmoking("No");
        userPreferences.setCommunication("Yes");
        user.setUserPreferences(userPreferences);
        Car car = mock(Car.class);
        user.setCars(Collections.singletonList(car));

        when(car.getId()).thenReturn(UUID.randomUUID()); // Mocking necessary fields of Car entity

        UserDTO userDTO = userMapper.userToUserDTO(user);

        // Assertions
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getBirthDate(), userDTO.getBirthDate());
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getCity(), userDTO.getCity());
        assertEquals(user.isDriver(), userDTO.isDriver());
        assertEquals(user.getCars().size(), userDTO.getCarsCount());
        assertEquals(user.getRating(), userDTO.getRating());
        assertEquals(user.getUserPreferences().getId(), userDTO.getUserPreferences().getId());
        assertEquals(user.getUserPreferences().getLanguage(), userDTO.getUserPreferences().getLanguage());
        assertEquals(user.getUserPreferences().getMusic(), userDTO.getUserPreferences().getMusic());
        assertEquals(user.getUserPreferences().getSmoking(), userDTO.getUserPreferences().getSmoking());
        assertEquals(user.getUserPreferences().getCommunication(), userDTO.getUserPreferences().getCommunication());
    }
}