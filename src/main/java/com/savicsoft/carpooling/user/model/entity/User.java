package com.savicsoft.carpooling.user.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
@Table(name="carpool_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String tel;
    private Date birthDate;
    private String country;
    private String city;
    private boolean isDriver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preferences_id")
    private UserPreferences userPreferences;

}
