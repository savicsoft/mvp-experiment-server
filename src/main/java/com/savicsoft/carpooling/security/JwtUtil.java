package com.savicsoft.carpooling.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String KEY = "no-one-is-gonna-guess-that";
    private final long EXPIRATION = 360_000_0; // 1 Hour

    private Claims retrieveAllClaims(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
    public <T> T retrieveClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = retrieveAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String createToken (Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }
    public String generateToken (String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    public Date extractExpiration(String token) {
        return retrieveClaim(token, Claims::getExpiration);
    }
    public boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return retrieveClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));

    }
}
