package com.savicsoft.carpooling.ride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// This DTO is intended to display a driver's scheduled rides along with information about passengers.
public class DriverPlannedRidesDTO {
    private UUID id;
    private UUID carId;
    private String carRegistrationNumber;
    private List<PassengerRideInfoDTO> passengers;
    private String startingPoint;
    private String endingPoint;
    private Short passengersLimit;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
}
