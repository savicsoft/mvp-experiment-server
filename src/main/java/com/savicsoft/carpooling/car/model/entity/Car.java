package com.savicsoft.carpooling.car.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.savicsoft.carpooling.car.enumeration.FuelType;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @JsonIgnore
    @OneToOne (mappedBy = "car", cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;
    @Unique
    @NotEmpty(message = "Registration number must be added")
    private String registrationNumber;
    private String color;
    private int year;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private double fuelEfficiency;
}



