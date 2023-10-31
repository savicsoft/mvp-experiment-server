package com.savicsoft.carpooling.car.service.impl;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.dto.mapper.CarDTOMapper;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.savicsoft.carpooling.car.dto.mapper.CarDTOMapper.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public List<CarDTO> getAllUsersCars(Long id) {
        return INSTANCE.mapToCarDTOList(carRepository.findAllByUserId(id));
    }

    @Override
    public List<CarDTO> getAllCars() {
        return INSTANCE.mapToCarDTOList(carRepository.findAll());
    }

    @Override
    public void deleteCarsByUserId(Long id) {
        carRepository.deleteCarsByUserId(id);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void deleteByRegistrationNumber(String registration) {
        carRepository.deleteByRegistrationNumber(registration);
    }

    @Override
    public CarDTO getCarByRegistrationNumber(String registration) {
        return INSTANCE.mapToCarDTO(carRepository.findCarByRegistrationNumber(registration));
    }

    @Override
    public CarDTO getCarById(Long id) {
        return INSTANCE.mapToCarDTO(carRepository.findCarById(id));
    }

    @Override
    public CarDTO createCar(Car car) {
        return INSTANCE.mapToCarDTO(carRepository.save(car));
    }

    @Override
    public CarDTO getCarByUUID(UUID uuid) {
        return INSTANCE.mapToCarDTO(carRepository.findCarByUuid(uuid));
    }
}
