package com.savicsoft.carpooling.security.payload;


import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
// ready to be removed
public class SignupRequest {

    @NotNull
    @Email
    final String email;

    @NotNull
    final String password;

    @NotNull
    final String firstName;
    @NotNull
    final String lastName;


    final Date birthDate;

    @NotNull
    final String country;

    @NotNull
    final String city;

    final Boolean driver;

}