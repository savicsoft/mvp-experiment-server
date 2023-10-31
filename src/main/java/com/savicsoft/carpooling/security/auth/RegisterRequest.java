package com.savicsoft.carpooling.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    // for date builder
    private int year;
    private int month;
    private int day;
    //
    private String country;
    private String city;
    private boolean isDriver;
    // user preferences
    private String language;
    private String music;
    private String smoking;
    private String communication;
}
