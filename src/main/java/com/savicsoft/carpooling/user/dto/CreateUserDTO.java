package com.savicsoft.carpooling.user.dto;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.Role;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;


import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDTO {
    private Long id;
    private UUID uuid;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private String city;
    private boolean driver;
    private Collection<Car> cars;
    private UserPreferences userPreferences;
    private Role role;
    private String pictureUrl;
}
