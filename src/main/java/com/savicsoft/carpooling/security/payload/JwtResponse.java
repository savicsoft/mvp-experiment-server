package com.savicsoft.carpooling.security.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class JwtResponse {
    final String token;
    final String type = "Bearer";
    final Long id;
    final String username;
    final String email;

}