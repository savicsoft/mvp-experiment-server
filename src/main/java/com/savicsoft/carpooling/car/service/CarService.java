package com.savicsoft.carpooling.car.service;

import com.savicsoft.carpooling.car.forms.UpdateCarInfoForm;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarDTO> getAllCarsOfUser(UUID uuid);
    CarDTO getCarById(UUID uuid);
    CarDTO createCar(Car car);
    CarDTO updateCar(UUID carId, UpdateCarInfoForm car);
    List<CarDTO> deleteCarsByUserId(UUID id);
    CarDTO deleteCarById(UUID carId);
}
