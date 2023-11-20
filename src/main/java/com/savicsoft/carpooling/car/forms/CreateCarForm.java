package com.savicsoft.carpooling.car.forms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateCarForm {
    @NotEmpty(message = "Registration number cannot be empty")
    private String registrationNumber;
    @NotEmpty(message = "Color cannot be empty")
    private String color;
    @NotNull(message = "Year cannot be empty")
    private int year;
    @NotNull(message = "Fuel efficiency cannot be empty")
    private double fuelEfficiency;
    @NotNull(message = "User id cannot be empty")
    private UUID userUUID;
    @NotEmpty(message = "Fuel type cannot be empty")
    private String fuelType;
    private List<String> pictureUrl;

}
