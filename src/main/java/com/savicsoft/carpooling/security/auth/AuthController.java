package com.savicsoft.carpooling.security.auth;

import com.savicsoft.carpooling.security.payload.JwtResponse;
import com.savicsoft.carpooling.security.payload.LoginRequest;
import com.savicsoft.carpooling.security.payload.MessageResponse;
import com.savicsoft.carpooling.security.payload.SignupRequest;
import com.savicsoft.carpooling.security.services.UserDetailsImpl;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import com.savicsoft.carpooling.user.repository.UserPreferencesRepository;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    final JavaMailSender mailSender;

    final UserService service;

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "testmvpm@gmail.com";
        String senderName = "Savicsoft";
        String subject = "Please verify your registration";
        String content = ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Test MVP.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create default preferences for the user

        UserPreferences preferences = UserPreferences.builder()
                .language("English")
                .smoking("No smoking")
                .communication("No communication")
                .music("No music")
                .build();
        //random string for verification code
        String randomCode = UUID.randomUUID().toString();
        // Create new user's account
        //Need to have password confirmation?
        var user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .firstName(signUpRequest.getFirstname())
                .lastName(signUpRequest.getLastname())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .userPreferences(preferences)
                //you dont need to send verification code or enabled to postman
                .verificationCode(randomCode)
                .enabled(false)
                .build();



        userRepository.save(user);
        userPreferencesRepository.save(preferences);
        sendVerificationEmail(user, "http://localhost:8080/api/auth");
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}