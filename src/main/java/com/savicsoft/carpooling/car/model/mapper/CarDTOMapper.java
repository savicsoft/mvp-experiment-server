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

    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "userId", source = "user.id")
    CarDTO mapToCarDTO(Car car);

    List<CarDTO> mapToCarDTOList(List<Car> cars);
}



