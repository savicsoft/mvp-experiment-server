package com.savicsoft.carpooling.user.dto;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDTO {
    Long id;
    UUID uuid;
    String password;
    String email;
    String tel;
    String firstName;
    String lastName;
    Date birthDate;
    String country;
    String city;
    boolean driver;
    Car car;
    UserPreferences userPreferences;
    String pictureUrl;
    String verificationCode;

    boolean enabled;
}
