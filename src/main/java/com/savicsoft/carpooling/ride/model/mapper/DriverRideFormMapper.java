package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.form.DriverRideForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverRideFormMapper {

    DriverRideFormMapper INSTANCE = Mappers.getMapper(DriverRideFormMapper.class);

    @Mapping(target = "id", ignore = true)
    DriverRide formToEntity(DriverRideForm form);
}
