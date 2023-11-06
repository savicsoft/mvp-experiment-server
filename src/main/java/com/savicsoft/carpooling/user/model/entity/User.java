package com.savicsoft.carpooling.user.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.Collection;
import jakarta.validation.constraints.*;

import javax.management.relation.Role;


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
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private Date birthDate;
    @NotBlank
    private String country;
    @NotBlank
    private String city;

    private boolean isDriver;

    //This was always required for creating the table, someone should look into it later
    private Long user_preferences_id;


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<Car> cars;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "car_id")
//    private Car car;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preferences_id", insertable = false, updatable = false)
    private UserPreferences userPreferences;

    //The all args constructor was always asking for id and UUID so I created this constructor
    public User(String firstName, String lastName, Date birthDate, String country, String city, boolean driver, String email, String password) {
    this.firstName=firstName;
    this.lastName=lastName;
    this.birthDate=birthDate;
    this.country=country;
    this.city=city;
    this.isDriver=driver;
    this.email=email;
    this.password=password;
    this.user_preferences_id = this.id;
    }

    //UserDetails of Spring Boot keep asking for username , so I added this otherwise wouldnt accepct the constructor
    public String getUsername(){
        return  "void";
    }


    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }
    @PrePersist
    public void autofill() {
        this.setUuid(UUID.randomUUID().toString());
    }






}
