package com.savicsoft.carpooling.ride.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PassengerRideSearchForm {
    private UUID passengerId;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime pickUpTime;
}
