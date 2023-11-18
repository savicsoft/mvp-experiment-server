package com.savicsoft.carpooling.car.dto.mapper;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface CarDTOMapper {
    CarDTOMapper INSTANCE = Mappers.getMapper(CarDTOMapper.class);
    CarDTO mapToCarDTO(Car user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fuelEfficiency", ignore = true)
    Car mapToCar(CarDTO userDTO);
    List<CarDTO> mapToCarDTOList(List<Car> car);
}



