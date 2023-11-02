package com.savicsoft.carpooling.user.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.UUID;
import java.util.Collection;


@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="carpool_user")
public class User {
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
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "car_id")
//    private Car car;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preferences_id")
    UserPreferences userPreferences;

}
