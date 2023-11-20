package com.savicsoft.carpooling.ride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

// This DTO is designed to showcase scheduled rides registered by drivers.
// Passengers will receive this information when searching for a ride.

public class DriverOfferedRideDTO {
    private UUID id;
    private UUID driverId;

    //need some user rating column to add here
    private String driverFirstName;
    private String driverLastName;
    private UUID carId;
    private String carRegistrationNumber;
    private String carColor;
    private int carYearOfManufacture;

    //add car model type attributes later
    private String startingPoint;
    private String endingPoint;
    private Short passengersLimit;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private BigDecimal calculatedPriceForPassenger;
}
