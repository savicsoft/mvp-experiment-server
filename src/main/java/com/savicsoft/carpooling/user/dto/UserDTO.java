package com.savicsoft.carpooling.user.dto;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
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
    boolean isDriver;
    Car car;
    UserPreferences userPreferences;

}
