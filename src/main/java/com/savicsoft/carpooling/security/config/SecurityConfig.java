package com.savicsoft.carpooling.security.config;

import com.savicsoft.carpooling.security.auth.AuthEntryPointJwt;
import com.savicsoft.carpooling.security.auth.AuthTokenFilter;
import com.savicsoft.carpooling.security.services.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.util.UUID;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
        (securedEnabled = true,
jsr250Enabled = true,
prePostEnabled = true)
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SecurityConfig {

    final UserDetailsServiceImpl userDetailsService;
    final AuthEntryPointJwt unauthorizedHandler;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String googleRedirectUri;
    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    String facebookClientId;
    @Value(("${spring.security.oauth2.client.registration.facebook.client-secret}"))
    String facebookClientSecret;
    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    String facebookRedirectUri;


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
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .anonymous(Customizer.withDefaults())
                .formLogin(fl-> fl.usernameParameter("email"))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/signin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers("/api/auth/signup").anonymous()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())

                .cors(cors-> cors.disable())
                        .logout(l-> l.logoutSuccessUrl("/")
                                .logoutUrl("/logout"));

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        http
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/my-clie"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }



    @Bean
        public RegisteredClientRepository registeredClientRepository () {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("products-client")
                    .clientSecret("{noop}secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .scope("products.read")
                    .scope("products.write")
                    .build();

            return new InMemoryRegisteredClientRepository(registeredClient);
        }
        @Bean
        public ClientRegistrationRepository clientRegistrationRepository () {
            return new InMemoryClientRegistrationRepository(this.facebookClientRegistration(), this.googleClientRegistration()
                  );
        }
        private ClientRegistration googleClientRegistration () {
            return ClientRegistration.withRegistrationId("google")
                    .clientId(googleClientId)
                    .clientSecret(googleClientSecret)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(googleRedirectUri)
                    .scope("openid", "profile", "email", "address", "phone")
                    .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                    .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                    .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                    .clientName("Google")
                    .build();
        }

        private ClientRegistration facebookClientRegistration () {
            return ClientRegistration.withRegistrationId("facebook")
                    .clientId(facebookClientId)
                    .clientSecret(facebookClientSecret)
                    .scope("public_profile", "email")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(facebookRedirectUri)
                    .authorizationUri("https://www.facebook.com/v2.8/dialog/oauth")
                    .tokenUri("https://graph.facebook.com/v2.8/oauth/access_token")
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email")
                    .userNameAttributeName("id")
                    .clientName("Facebook")
                    .build();
        }

    }