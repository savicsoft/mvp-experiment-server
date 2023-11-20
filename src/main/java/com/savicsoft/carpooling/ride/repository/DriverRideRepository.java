package com.savicsoft.carpooling.ride.repository;

import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRideRepository extends JpaRepository<DriverRide, Long> {
    List<DriverRide> findAllByDriverId(UUID driverId);

    Optional<DriverRide> findById(UUID id);
    void deleteById(UUID id);

}
