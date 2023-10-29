package com.savicsoft.carpooling.car.forms;

import com.savicsoft.carpooling.car.enumeration.FuelType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarForm {
    @NotEmpty(message = "Registration number cannot be empty")
    private String registrationNumber;
    @NotEmpty(message = "Color cannot be empty")
    private String color;
    @NotNull(message = "Year cannot be empty")
    private int year;
    @NotNull(message = "Fuel efficiency cannot be empty")
    private double fuelEfficiency;
    @NotNull(message = "User id cannot be empty")
    private Long userId;
    @NotEmpty(message = "Fuel type cannot be empty")
    private String fuelType;

}
