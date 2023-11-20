package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.dto.DriverOfferedRideDTO;
import com.savicsoft.carpooling.ride.model.dto.DriverPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.dto.PassengerRideInfoDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface DriverPlannedRidesMapper {

    DriverPlannedRidesMapper INSTANCE = Mappers.getMapper(DriverPlannedRidesMapper.class);
    @Mapping(source = "driverRide.car.id", target = "carId")
    @Mapping(source = "driverRide.car.registrationNumber", target = "carRegistrationNumber")
    @Mapping(source = "passengerRides", target = "passengers", qualifiedByName = "mapPassengerRides")
    DriverPlannedRidesDTO driverRideToDriverPlannedRidesDTO(DriverRide driverRide, List<PassengerRide> passengerRides);

    @Named("mapPassengerRides")
    default List<PassengerRideInfoDTO> mapPassengerRides(List<PassengerRide> passengerRides) {
        return passengerRides.stream()
                .map(PassengerRideInfoMapper.INSTANCE::passengerRideToPassengerRideInfoDTO)
                .collect(Collectors.toList());
    }

    default List<DriverPlannedRidesDTO> driverRideToDriverPlannedRidesDTOs(Map<DriverRide, List<PassengerRide>> driverRidesWithPassengers) {
        if (driverRidesWithPassengers == null) {
            return Collections.emptyList();
        }

        List<DriverPlannedRidesDTO> dtos = new ArrayList<>();
        for (Map.Entry<DriverRide, List<PassengerRide>> entry : driverRidesWithPassengers.entrySet()) {
            dtos.add(driverRideToDriverPlannedRidesDTO(entry.getKey(), entry.getValue()));
        }
        return dtos;
    }

}
