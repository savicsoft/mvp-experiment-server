package com.savicsoft.carpooling.user.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Collection;


@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="carpool_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
    String email;
    String password;
    String firstName;
    String lastName;
    String tel;
    Date birthDate;
    String country;
    String city;
    @Column(name="is_driver")
    boolean driver;//if this attribute's name == `isDriver`, Lombok generates an `isDriver` getter and `setDriver` setter, causing problems with MapStruct

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<Car> cars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preferences_id")
    UserPreferences userPreferences;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
