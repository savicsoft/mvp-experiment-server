package com.savicsoft.carpooling.car.model.mapper;

import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCarMapper {
    private final UserRepository userRepository;

    public Car mapToCar(CarDTO carDTO){

        return Car.builder()
                .id(carDTO.getId())
                .user(userRepository.findById(carDTO.getUserId()).orElse(null))
                .mark(carDTO.getMark())
                .model(carDTO.getModel())
                .registrationNumber(carDTO.getRegistrationNumber())
                .color(carDTO.getColor())
                .year(carDTO.getYear())
                .fuelType(carDTO.getFuelType())
                .fuelEfficiency(carDTO.getFuelEfficiency())
                .numOfPics(carDTO.calculateNumOfPics())
                .build();
    }
}