package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.dto.PassengerPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public interface PassengerPlannedRidesMapper {
    PassengerPlannedRidesMapper INSTANCE = Mappers.getMapper(PassengerPlannedRidesMapper.class);


    @Mapping(source = "passengerRide.driverRide.id", target = "driverRideId")
    @Mapping(source = "passengerRide.driverRide.driver.id", target = "driverId")
    @Mapping(source = "passengerRide.driverRide.driver.firstName", target = "driverFirstName")
    @Mapping(source = "passengerRide.driverRide.driver.lastName", target = "driverLastName")
    @Mapping(source = "passengerRide.driverRide.car.id", target = "carId")
    @Mapping(source = "passengerRide.driverRide.car.registrationNumber", target = "carRegistrationNumber")
    @Mapping(source = "passengerRide.driverRide.car.color", target = "carColor")
    @Mapping(source = "passengerRide.driverRide.car.year", target = "carYearOfManufacture")
    PassengerPlannedRidesDTO passengerRideToPassengerPlannedRidesDTO(PassengerRide passengerRide);

    default List<PassengerPlannedRidesDTO> passengerRideToPassengerPlannedRidesDTOs(List<PassengerRide> passengerPlannedRides) {
        if (passengerPlannedRides == null) {
            return Collections.emptyList();
        }

        List<PassengerPlannedRidesDTO> dtos = new ArrayList<>();
        for (PassengerRide passengerPlannedRide : passengerPlannedRides) {
            dtos.add(passengerRideToPassengerPlannedRidesDTO(passengerPlannedRide));
        }
        return dtos;
    }
}
