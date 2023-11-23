package com.savicsoft.carpooling.user.model.dto;

import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String email;

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private String city;
    private boolean driver;
    private int carsCount;
    private MultipartFile picture;
    private double rating;
    private UserPreferences userPreferences;
}
