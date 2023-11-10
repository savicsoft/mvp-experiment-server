package com.savicsoft.carpooling.user.dto;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    Long id;
    UUID uuid;
    String email;
    String tel;
    String firstName;
    String lastName;
    Date birthDate;
    String country;
    String city;
    //if this attribute's name == `isDriver`, Lombok generates an `isDriver` getter and `setDriver` setter, causing problems with MapStruct
    boolean driver;
    Car car;
    UserPreferences userPreferences;
    String pictureUrl;
}
