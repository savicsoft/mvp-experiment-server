package com.savicsoft.carpooling.ride.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PassengerRideSaveForm {
    private UUID passengerId;
    private UUID driverRideId;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime pickUpTime;
    private BigDecimal ridePrice;

}
