package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.dto.DriverOfferedRideDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper
public interface DriverOfferedRideMapper {

    DriverOfferedRideMapper INSTANCE = Mappers.getMapper(DriverOfferedRideMapper.class);

    @Mapping(source = "driverRide.driver.id", target = "driverId")
    @Mapping(source = "driverRide.driver.firstName", target = "driverFirstName")
    @Mapping(source = "driverRide.driver.lastName", target = "driverLastName")
    @Mapping(source = "driverRide.car.id", target = "carId")
    @Mapping(source = "driverRide.car.registrationNumber", target = "carRegistrationNumber")
    @Mapping(source = "driverRide.car.color", target = "carColor")
    @Mapping(source = "driverRide.car.year", target = "carYearOfManufacture")
    @Mapping(source = "calculatedPrice", target = "calculatedPriceForPassenger")
    DriverOfferedRideDTO driverRideToDriverOfferedRideDTO(DriverRide driverRide, BigDecimal calculatedPrice);

    default List<DriverOfferedRideDTO> driverRidesToDriverOfferedRideDTOs(Map<DriverRide, BigDecimal> driverRidesWithPrices) {
        if (driverRidesWithPrices == null) {
            return Collections.emptyList();
        }

        List<DriverOfferedRideDTO> dtos = new ArrayList<>();
        for (Map.Entry<DriverRide, BigDecimal> entry : driverRidesWithPrices.entrySet()) {
            dtos.add(driverRideToDriverOfferedRideDTO(entry.getKey(), entry.getValue()));
        }
        return dtos;
    }
}