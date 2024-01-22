package com.savicsoft.carpooling.security.auth.oauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/oauth/login")
public class OauthController {
    @GetMapping("/google")
    public String OauthGoogleLogin() {
        // Redirect to Google OAuth2 login page for now
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/facebook")
    public String OauthFacebookLogin() {
        // Redirect to Facebook OAuth2 login page for now
        return "redirect:/oauth2/authorization/facebook";
    }

}
