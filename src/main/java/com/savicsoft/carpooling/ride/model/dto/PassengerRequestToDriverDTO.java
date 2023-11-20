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
// DTO providing information to the driver
// about passengers who have chosen his ride and are awaiting approval
public class PassengerRequestToDriverDTO {
    private UUID id;
    private UUID passengerId;

    //need some user rating column to add here
    private String passengerFirstName;
    private String passengerLastName;
    private UUID driverRideId;
    private String rideStartingPoint;
    private String rideEndingPoint;
    private Short passengersLimit;
    private Short currentPassengersAmount;
    private LocalDateTime rideStartingTime;
    private LocalDateTime rideEndingTime;
    private String passengerStartingPoint;
    private String passengerEndingPoint;
    private LocalDateTime pickUpTime;
    private BigDecimal ridePrice;
}
