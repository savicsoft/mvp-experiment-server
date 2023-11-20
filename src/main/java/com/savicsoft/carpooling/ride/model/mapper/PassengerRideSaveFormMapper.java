package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.form.PassengerRideSaveForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerRideSaveFormMapper {

        PassengerRideSaveFormMapper INSTANCE = Mappers.getMapper(PassengerRideSaveFormMapper.class);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "rideStatus", constant = "Requested") // Set an initial status, adjust as needed
        PassengerRide formToEntity(PassengerRideSaveForm form);

}
