package com.savicsoft.carpooling.user.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private UUID uuid;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private String city;
    //if this attribute's name == `isDriver`, Lombok generates an `isDriver` getter and `setDriver` setter, causing problems with MapStruct
    private boolean driver;
    private int carsCount;
    private int user_preferences_id;
    private String pictureUrl;
}
