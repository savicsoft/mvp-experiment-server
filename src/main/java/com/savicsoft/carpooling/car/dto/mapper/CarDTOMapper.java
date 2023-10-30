package com.savicsoft.carpooling.car.dto.mapper;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

public class CarDTOMapper {
    public static CarDTO toDto(Car car){
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(car,carDTO);
        return carDTO;
    }

}
