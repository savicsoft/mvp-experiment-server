package com.savicsoft.carpooling.security.payload;


import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SignupRequest {
    @NotBlank(message = "Email Address is needed")
    @Email
    final String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).*$",
            message = "Password should contain at least 1 uppercase and 1 special Character")
    @Size(min = 6, message = "Password Must be at least 6 characters")
    final String password;
}