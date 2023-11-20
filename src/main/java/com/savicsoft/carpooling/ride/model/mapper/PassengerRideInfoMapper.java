package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.dto.PassengerRideInfoDTO;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public interface PassengerRideInfoMapper {
    PassengerRideInfoMapper INSTANCE = Mappers.getMapper(PassengerRideInfoMapper.class);

    @Mapping(source = "passengerRide.passenger.id", target = "passengerId")
    @Mapping(source = "passengerRide.passenger.firstName", target = "passengerFirstName")
    @Mapping(source = "passengerRide.passenger.lastName", target = "passengerLastName")
    PassengerRideInfoDTO passengerRideToPassengerRideInfoDTO(PassengerRide passengerRide);
    default List<PassengerRideInfoDTO> passengerRideToPassengerRideInfoDTOs(List<PassengerRide> passengerRides) {
        if (passengerRides == null) {
            return Collections.emptyList();
        }

        List<PassengerRideInfoDTO> dtos = new ArrayList<>();
        for (PassengerRide passengerRide : passengerRides) {
            dtos.add(passengerRideToPassengerRideInfoDTO(passengerRide));
        }
        return dtos;
    }
}
