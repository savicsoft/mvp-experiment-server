package com.savicsoft.carpooling.car.service.impl;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public List<Car> getAllUsersCars(Long id) {
        return carRepository.findAllByUserId(id);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCarsByUserId(Long id) {
        carRepository.deleteCarsByUserId(id);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteByCarId(id);
    }

    @Override
    public void deleteByRegistrationNumber(String registration) {
        carRepository.deleteByRegistrationNumber(registration);
    }

    @Override
    public Car getCarByRegistrationNumber(String registration) {
        return carRepository.findCarByRegistrationNumber(registration);
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findCarByCarId(id);
    }

    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car getCarByUUID(UUID uuid) {
        return carRepository.findCarByUuid(uuid);
    }
}
