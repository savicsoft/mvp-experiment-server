package com.savicsoft.carpooling.ride.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DriverRideForm {
    private UUID driverId;
    private UUID carId;
    private String startingPoint;
    private String endingPoint;
    private Short passengersLimit;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
}
