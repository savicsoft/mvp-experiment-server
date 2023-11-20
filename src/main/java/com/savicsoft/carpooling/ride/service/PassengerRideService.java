package com.savicsoft.carpooling.ride.service;

import com.savicsoft.carpooling.ride.model.dto.PassengerPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.dto.PassengerRequestToDriverDTO;
import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import com.savicsoft.carpooling.ride.model.form.PassengerRideSaveForm;

import java.util.List;
import java.util.UUID;

public interface PassengerRideService {
    List<PassengerPlannedRidesDTO> getPassengerPlannedRides(UUID passengerId);

    List<PassengerRequestToDriverDTO> getAllPassengerRequests(UUID driverId);

    boolean deletePassengerRide(UUID passengerRideId);
    boolean savePassengerRide(PassengerRideSaveForm passengerRideSaveForm);
}
