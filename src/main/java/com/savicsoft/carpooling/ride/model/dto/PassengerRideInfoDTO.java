package com.savicsoft.carpooling.ride.model.dto;

import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
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

// DTO designed to display information about approved passengers to drivers.
public class PassengerRideInfoDTO {
    private UUID id;
    private UUID passengerId;

    //need some user rating column to add here
    private String passengerFirstName;
    private String passengerLastName;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime pickUpTime;
    private BigDecimal ridePrice;
    private RideStatus rideStatus;

}
