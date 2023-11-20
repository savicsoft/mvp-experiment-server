package com.savicsoft.carpooling.ride.repository;


import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassengerRideRepository extends JpaRepository<PassengerRide, Long> {

     Optional<PassengerRide> findById(UUID id);

     List<PassengerRide> findAllByDriverRideId(UUID driverRideId);
     List<PassengerRide> findAllByDriverRideIdAndRideStatus(UUID driverRideId, RideStatus rideStatus);
     List<PassengerRide> findAllByPassengerId(UUID passengerId);
     Short countByDriverRideId(UUID driverRideId);

     void deleteById(UUID id);


}
