package com.savicsoft.carpooling.ride.service.impl;

import com.savicsoft.carpooling.ride.model.dto.PassengerPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.dto.PassengerRequestToDriverDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import com.savicsoft.carpooling.ride.model.form.PassengerRideSaveForm;
import com.savicsoft.carpooling.ride.model.mapper.PassengerPlannedRidesMapper;
import com.savicsoft.carpooling.ride.model.mapper.PassengerRequestRideMapper;
import com.savicsoft.carpooling.ride.model.mapper.PassengerRideSaveFormMapper;
import com.savicsoft.carpooling.ride.repository.DriverRideRepository;
import com.savicsoft.carpooling.ride.repository.PassengerRideRepository;
import com.savicsoft.carpooling.ride.service.PassengerRideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

// Next step is to add exception handlers
// Basic functions have been added to demonstrate working with the created DTOs.
@Service
@RequiredArgsConstructor
public class PassengerRideServiceImpl implements PassengerRideService {

    private  final PassengerRideRepository passengerRideRepository;
    private final DriverRideRepository driverRideRepository;
    @Override
    public List<PassengerPlannedRidesDTO> getPassengerPlannedRides(UUID passengerId) {
        List<PassengerRide> passengerRides = passengerRideRepository.findAllByPassengerId(passengerId);
        List<PassengerPlannedRidesDTO> plannedRides = PassengerPlannedRidesMapper.INSTANCE.passengerRideToPassengerPlannedRidesDTOs(passengerRides);
        return plannedRides;
    }

    @Override
    public List<PassengerRequestToDriverDTO> getAllPassengerRequests(UUID driverId) {
        List<DriverRide> driverRides = driverRideRepository.findAllByDriverId(driverId);
        Map<PassengerRide, Short> passengerRequests = new LinkedHashMap<>();
        for(DriverRide driverRide:driverRides){
            List<PassengerRide> passengers = passengerRideRepository
                    .findAllByDriverRideIdAndRideStatus(driverRide.getId(), RideStatus.Requested);
            for(PassengerRide passengerRequest:passengers){
                Short currentPassengersAmount = passengerRideRepository.countByDriverRideId(driverRide.getId());
                passengerRequests.put(passengerRequest, currentPassengersAmount);
            }
        }
        List<PassengerRequestToDriverDTO> passengerRequestsDTO = PassengerRequestRideMapper.INSTANCE.passengerRideToPassengerRequestRideDTOs(passengerRequests);
        return passengerRequestsDTO;
    }

    @Override
    public boolean savePassengerRide(PassengerRideSaveForm passengerRideSaveForm) {
        PassengerRide passengerRide = PassengerRideSaveFormMapper.INSTANCE.formToEntity(passengerRideSaveForm);
        passengerRideRepository.save(passengerRide);
        Optional<PassengerRide> savedPassengerRide = passengerRideRepository.findById(passengerRide.getId());
        return savedPassengerRide.isPresent();
    }

    @Override
    public boolean deletePassengerRide(UUID passengerRideId) {
        Optional<PassengerRide> passengerRide = passengerRideRepository.findById(passengerRideId);
        if (passengerRide.isPresent()) {
            passengerRideRepository.deleteById(passengerRideId);
            Optional<PassengerRide> deletedPassengerRide = passengerRideRepository.findById(passengerRideId);
            return !deletedPassengerRide.isPresent();
        }
        return false;
    }
}
