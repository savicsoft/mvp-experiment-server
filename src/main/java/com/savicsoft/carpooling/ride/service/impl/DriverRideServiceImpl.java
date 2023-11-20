package com.savicsoft.carpooling.ride.service.impl;

import com.savicsoft.carpooling.ride.model.dto.DriverOfferedRideDTO;
import com.savicsoft.carpooling.ride.model.dto.DriverPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.form.DriverRideForm;
import com.savicsoft.carpooling.ride.model.form.PassengerRideSearchForm;
import com.savicsoft.carpooling.ride.model.mapper.DriverOfferedRideMapper;
import com.savicsoft.carpooling.ride.model.mapper.DriverPlannedRidesMapper;
import com.savicsoft.carpooling.ride.model.mapper.DriverRideFormMapper;
import com.savicsoft.carpooling.ride.repository.DriverRideRepository;
import com.savicsoft.carpooling.ride.repository.PassengerRideRepository;
import com.savicsoft.carpooling.ride.service.DriverRideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;



// Next step is to add exception handlers
// Basic functions have been added to demonstrate working with the created DTOs.
@Service
@RequiredArgsConstructor
public class DriverRideServiceImpl implements DriverRideService {

    private final DriverRideRepository driverRideRepository;
    private final PassengerRideRepository passengerRideRepository;

    @Override
    public List<DriverOfferedRideDTO> getAvailableDriverRides(PassengerRideSearchForm passengerSearch) {


        // Implement an algorithm for available driver rides search based on passengers info
        // add automatic price calculation for each matching drive
        Map<DriverRide, BigDecimal> driverRidesWithPrices = new LinkedHashMap<>();

        List<DriverOfferedRideDTO> offeredRides = DriverOfferedRideMapper.INSTANCE.driverRidesToDriverOfferedRideDTOs(driverRidesWithPrices);
        return offeredRides;
    }

    @Override
    public List<DriverPlannedRidesDTO> getDriverPlannedRides(UUID driverId) {
        List<DriverRide> rides = driverRideRepository.findAllByDriverId(driverId);
        Map<DriverRide, List<PassengerRide>> driverRidesWithPassengers = new LinkedHashMap<>();
        for(DriverRide ride:rides){
            List<PassengerRide> passengers = passengerRideRepository.findAllByDriverRideId(ride.getId());
            driverRidesWithPassengers.put(ride, passengers);
        }
        List<DriverPlannedRidesDTO> plannedRides = DriverPlannedRidesMapper.INSTANCE.driverRideToDriverPlannedRidesDTOs(driverRidesWithPassengers);
        return plannedRides;
    }

    @Override
    public boolean deleteDriverRide(UUID driverRideId) {
        Optional<DriverRide> optionalDriverRide = driverRideRepository.findById(driverRideId);
        if (optionalDriverRide.isPresent()) {
            driverRideRepository.deleteById(driverRideId);
            Optional<DriverRide> deletedDriverRide = driverRideRepository.findById(driverRideId);
            return !deletedDriverRide.isPresent();
        }
        return false;
    }

    @Override
    public boolean saveDriverRide(DriverRideForm driverRideForm) {
        DriverRide driverRide = DriverRideFormMapper.INSTANCE.formToEntity(driverRideForm);
        driverRideRepository.save(driverRide);
        Optional<DriverRide> savedDriverRide = driverRideRepository.findById(driverRide.getId());
        return savedDriverRide.isPresent();
    }
}
