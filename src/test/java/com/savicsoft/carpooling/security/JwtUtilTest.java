package com.savicsoft.carpooling.security;

import com.savicsoft.carpooling.CarpoolingApplication;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource("/application.properties")
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;
    @Test
    public void testGenerateToken() {
        UserDetails userDetails = User.withUsername("user")
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);

        Assertions.assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String token = Jwts.builder()
                .subject("user@example.com")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(jwtUtil.getSignInKey())
                .compact();

        String username = jwtUtil.extractUsername(token);

        Assertions.assertEquals("user@example.com", username);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = User.withUsername("user")
                .password("password")
                .roles("USER")
                .build();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key1", "value1");
        extraClaims.put("key2", "value2");

        String token = jwtUtil.generateToken(extraClaims, userDetails);

        boolean isValid = jwtUtil.isTokenValid(token, userDetails);

        Assertions.assertTrue(isValid);
    }
}
