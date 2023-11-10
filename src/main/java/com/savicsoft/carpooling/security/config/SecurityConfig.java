package com.savicsoft.carpooling.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.core.annotation.Order;
import org.springframework.core.annotation.Order;
mport org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;


import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.savicsoft.carpooling.security.jwt.AuthEntryPointJwt;
import com.savicsoft.carpooling.security.jwt.AuthTokenFilter;
import com.savicsoft.carpooling.security.services.UserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
        (securedEnabled = true,
                jsr250Enabled = true,
                prePostEnabled = true)
public class SecurityConfig{
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors->cors.disable())
                .csrf(csrf->csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/auth/signin").permitAll()
                        .anyRequest().authenticated()

                )
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .cors(cors-> cors.disable());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        public RegisteredClientRepository registeredClientRepository () {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("products-client")
                    .clientId("products-client")
                    .clientSecret("{noop}secret")
                    .clientSecret("{noop}secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .scope("products.read")
                    .scope("products.read")
                    .scope("products.write")
                    .scope("products.write")
                    .build();
                    .build();

            return new InMemoryRegisteredClientRepository(registeredClient);
        }
        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository(this.facebookClientRegistration(), this.googleClientRegistration());
        }


        @Bean
        public ClientRegistrationRepository clientRegistrationRepository () {
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            return new InMemoryClientRegistrationRepository(this.facebookClientRegistration(), this.googleClientRegistration());
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        }

        private ClientRegistration googleClientRegistration () {
            return ClientRegistration.withRegistrationId("facebook")
            return ClientRegistration.withRegistrationId("google")
                    .clientId("328784993078427")
                    .clientId("358759268020-2nobses99s0vl397r5mhvpo610328fjt.apps.googleusercontent.com")
                    .clientSecret("e9d57cdff045e75dcb0f3c96e62e5160")
                    .clientSecret("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")
                    .scope("public_profile", "email")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .scope("openid", "profile", "email", "address", "phone")
                    .authorizationUri("https://www.facebook.com/v2.8/dialog/oauth")
                    .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                    .tokenUri("https://graph.facebook.com/v2.8/oauth/access_token")
                    .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email")
                    .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .userNameAttributeName("id")
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .clientName("Facebook")
                    .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                    .build();
                    .clientName("Google")
        }
                    .build();
    }
}


        private ClientRegistration facebookClientRegistration () {
            return ClientRegistration.withRegistrationId("facebook")
                    .clientId("328784993078427")
                    .clientSecret("e9d57cdff045e75dcb0f3c96e62e5160")
                    .scope("public_profile", "email")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .authorizationUri("https://www.facebook.com/v2.8/dialog/oauth")
                    .tokenUri("https://graph.facebook.com/v2.8/oauth/access_token")
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email")
                    .userNameAttributeName("id")
                    .clientName("Facebook")
                    .build();
        }

    }
}
