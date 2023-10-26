package com.savicsoft.carpooling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

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

import java.util.UUID;

/*To sign up
Send a POST request to {{URL}}/api/auth/signup with a JSON
{username, password, email}
To log in
{{UR}}/login
Or send a POST request to {{URL}}/api/auth/login with a JSON
{username, password}
*/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
        (securedEnabled = true,
jsr250Enabled = true,
prePostEnabled = true)
public class SecurityConfig {
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
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http
                // Redirect to the OAuth 2.0 Login endpoint when not authenticated
                // from the authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/my-client"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

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
                     .requestMatchers("/api/auth/signup").permitAll()
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
            return new InMemoryClientRegistrationRepository(this.facebookClientRegistration(), this.googleClientRegistration());
        }

        private ClientRegistration googleClientRegistration () {
            return ClientRegistration.withRegistrationId("google")
                    .clientId("358759268020-2nobses99s0vl397r5mhvpo610328fjt.apps.googleusercontent.com")
                    .clientSecret("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")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
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