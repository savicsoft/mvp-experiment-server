package com.savicsoft.carpooling.security.auth;

import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.security.config.JwtService;
import com.savicsoft.carpooling.user.model.entity.Role;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.repository.UserPreferencesRepository;
import com.savicsoft.carpooling.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) throws ParseException {
        // birthdate builder
        var birthDate = LocalDate.of(request.getYear(), request.getMonth(), request.getDay());

        var userPreferences = UserPreferences.builder()
                .language(request.getLanguage())
                .music(request.getMusic())
                .smoking(request.getSmoking())
                .communication(request.getCommunication())
                .build();

        var user = User.builder()
                .uuid(UUID.randomUUID())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(birthDate)
                .country(request.getCountry())
                .city(request.getCity())
                .driver(!request.isDriver())
                .userPreferences(userPreferences)
                .role(Role.USER)
                .build();

        userRepository.save(user);
        preferencesRepository.save(userPreferences);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}