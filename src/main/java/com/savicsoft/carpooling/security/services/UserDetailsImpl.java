package com.savicsoft.carpooling.security.services;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.savicsoft.carpooling.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {
    static final long serialVersionUID = 1L;

    final Long id;

    final String username;

    final String email;

    @JsonIgnore
    private String password;


    public UserDetailsImpl(Long id, String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }


    public Long getId() {
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
        return true;
    }

}