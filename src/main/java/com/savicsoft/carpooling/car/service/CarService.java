package com.savicsoft.carpooling.car.service;

import com.savicsoft.carpooling.car.model.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getAllUsersCars(Long id);
    List<Car> getAllCars();
    void deleteCarsByUserId(Long id);
    void deleteById(Long id);
    void deleteByRegistrationNumber(String registration);
    Car getCarByRegistrationNumber(String registration);
    Car getCarById(Long id);
    Car createCar(Car car);
    Car getCarByUUID(UUID uuid);

}
