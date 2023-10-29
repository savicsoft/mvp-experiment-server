package com.savicsoft.carpooling.user.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Date birthDate;
    private String country;
    private String city;
    private boolean isDriver;

//    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private Collection<Car> cars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preferences_id")
    private UserPreferences userPreferences;
}
