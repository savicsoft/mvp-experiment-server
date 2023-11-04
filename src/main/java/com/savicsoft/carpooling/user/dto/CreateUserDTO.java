package com.savicsoft.carpooling.user.dto;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.Role;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;
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
    LocalDate birthDate;
    String country;
    String city;
    boolean driver;
    Collection<Car> cars;
    UserPreferences userPreferences;
    Role role;
}
