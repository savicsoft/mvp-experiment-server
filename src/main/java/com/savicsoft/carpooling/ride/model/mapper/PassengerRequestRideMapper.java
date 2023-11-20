package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.dto.PassengerRequestToDriverDTO;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper
public interface PassengerRequestRideMapper {
    PassengerRequestRideMapper INSTANCE = Mappers.getMapper(PassengerRequestRideMapper.class);

    @Mapping(source = "passengerRide.passenger.id", target = "passengerId")
    @Mapping(source = "passengerRide.passenger.firstName", target = "passengerFirstName")
    @Mapping(source = "passengerRide.passenger.lastName", target = "passengerLastName")
    @Mapping(source = "passengerRide.driverRide.id", target = "driverRideId")
    @Mapping(source = "passengerRide.driverRide.startingPoint", target = "rideStartingPoint")
    @Mapping(source = "passengerRide.driverRide.endingPoint", target = "rideEndingPoint")
    @Mapping(source = "passengerRide.driverRide.passengersLimit", target = "passengersLimit")
    @Mapping(source = "passengerRide.driverRide.startingTime", target = "rideStartingTime")
    @Mapping(source = "passengerRide.driverRide.endingTime", target = "rideEndingTime")
    @Mapping(source = "passengerRide.startingPoint", target = "passengerStartingPoint")
    @Mapping(source = "passengerRide.endingPoint", target = "passengerEndingPoint")
    PassengerRequestToDriverDTO passengerRideToPassengerRequestRideDTO(PassengerRide passengerRide, Short currentPassengersAmount);
    default List<PassengerRequestToDriverDTO> passengerRideToPassengerRequestRideDTOs(Map<PassengerRide, Short> passengerRequests) {
        if (passengerRequests == null) {
            return Collections.emptyList();
        }

        List<PassengerRequestToDriverDTO> dtos = new ArrayList<>();
        for (Map.Entry<PassengerRide, Short> entry : passengerRequests.entrySet()) {
            dtos.add(passengerRideToPassengerRequestRideDTO(entry.getKey(), entry.getValue()));
        }
        return dtos;
    }
}
