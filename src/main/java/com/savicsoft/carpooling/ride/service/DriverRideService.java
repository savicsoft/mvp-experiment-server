package com.savicsoft.carpooling.ride.service;

import com.savicsoft.carpooling.ride.model.dto.DriverOfferedRideDTO;
import com.savicsoft.carpooling.ride.model.dto.DriverPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.dto.PassengerRequestToDriverDTO;
import com.savicsoft.carpooling.ride.model.form.DriverRideForm;
import com.savicsoft.carpooling.ride.model.form.PassengerRideSearchForm;

import java.util.List;
import java.util.UUID;

public interface DriverRideService {
    List<DriverOfferedRideDTO> getAvailableDriverRides(PassengerRideSearchForm passengerSearch);

    List<DriverPlannedRidesDTO> getDriverPlannedRides(UUID driverId);


    boolean deleteDriverRide(UUID driverRideId);

    boolean saveDriverRide(DriverRideForm driverRideForm);


}
