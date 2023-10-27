package com.savicsoft.carpooling.user.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
/*
    String firstName;
    String lastName;
    String email;
    String tel;
    LocalDate birthdate;
    //Create class for location?
    String Country;
    String City;
*/
}
