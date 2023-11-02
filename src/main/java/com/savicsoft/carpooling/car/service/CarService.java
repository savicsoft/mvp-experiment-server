package com.savicsoft.carpooling.car.service;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarDTO> getAllUsersCars(Long id);
    List<CarDTO> getAllCars();
    void deleteCarsByUserId(Long id);
    void deleteById(Long id);
    void deleteByRegistrationNumber(String registration);
    CarDTO getCarByRegistrationNumber(String registration);
    CarDTO getCarById(Long id);
    CarDTO createCar(Car car);
    CarDTO getCarByUUID(UUID uuid);

}
