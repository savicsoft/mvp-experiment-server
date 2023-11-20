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
// This DTO is created to present passengers with information about their scheduled rides.
public class PassengerPlannedRidesDTO {
    private UUID id;
    private UUID driverRideId;

    private UUID driverId;
    private String driverFirstName;
    private String driverLastName;
    private UUID carId;
    private String carRegistrationNumber;
    private String carColor;
    private int carYearOfManufacture;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime pickUpTime;
    private BigDecimal ridePrice;
    private RideStatus rideStatus;

}
