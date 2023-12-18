package com.savicsoft.carpooling.security.services;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.savicsoft.carpooling.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {
    static final long serialVersionUID = 1L;

    final UUID id;

    final String username;

    final String email;

    @JsonIgnore
    final String password;

    final boolean enabled;

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled());
    }


    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean getEnabled() {return  enabled;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;}

}