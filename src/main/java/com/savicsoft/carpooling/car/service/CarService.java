package com.savicsoft.carpooling.car.service;

import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarDTO> getAllCarsOfUser(UUID uuid);
    List<CarDTO> getAllCars();
    void deleteCarsByUserId(Long id);
    CarDTO createCar(Car car);
    CarDTO getCarByUUID(UUID uuid);

}
