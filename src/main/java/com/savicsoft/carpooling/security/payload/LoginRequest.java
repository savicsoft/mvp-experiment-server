package com.savicsoft.carpooling.security.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "Please enter a valid email")
    private String email;

    @NotNull(message = "Please enter your password")
    private String password;
}