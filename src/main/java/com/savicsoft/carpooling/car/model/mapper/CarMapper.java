package com.savicsoft.carpooling.car.model.mapper;

import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface CarDTOMapper {
    CarDTOMapper INSTANCE = Mappers.getMapper(CarDTOMapper.class);
    CarDTO mapToCarDTO(Car user);
    Car mapToCar(CarDTO userDTO);
    List<CarDTO> mapToCarDTOList(List<Car> car);
}



