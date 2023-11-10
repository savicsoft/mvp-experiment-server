package com.savicsoft.carpooling.security.auth;

import com.savicsoft.carpooling.security.payload.JwtResponse;
import com.savicsoft.carpooling.security.payload.LoginRequest;
import com.savicsoft.carpooling.security.payload.MessageResponse;
import com.savicsoft.carpooling.security.payload.SignupRequest;
import com.savicsoft.carpooling.security.services.UserDetailsImpl;
import com.savicsoft.carpooling.user.model.entity.Role;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.repository.UserPreferencesRepository;
import com.savicsoft.carpooling.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AuthController {
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final JwtUtils jwtUtils;
    final UserPreferencesRepository userPreferencesRepository;
    final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        var user = User.builder()
                .uuid(UUID.randomUUID())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .birthDate(signUpRequest.getBirthDate())
                .country(signUpRequest.getCountry())
                .city(signUpRequest.getCity())
                .driver(!signUpRequest.getDriver())
                .tel(signUpRequest.getTel())
                .userPreferences(preferences)
                .role(Role.USER)
                .build();

        userRepository.save(user);
        userPreferencesRepository.save(preferences);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}