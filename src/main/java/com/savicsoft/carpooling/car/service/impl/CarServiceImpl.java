package com.savicsoft.carpooling.car.service.impl;

import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.mapper.CarDTOMapper;
import com.savicsoft.carpooling.exception.CouldNotUpdateException;
import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.exception.CouldNotCreateException;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.car.forms.UpdateCarInfoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public List<CarDTO> getAllCarsOfUser(UUID uuid) {
        Optional<List<Car>> carsOptional = carRepository.findAllByUserId(uuid);
        if (carsOptional.isEmpty())
            throw new NotFoundException("User does not exist for UUID: " + uuid + ". Please, check the entered input");
        return CarDTOMapper.INSTANCE.mapToCarDTOList(carsOptional.get());
    }

    @Override
    public CarDTO getCarById(UUID id) {
        Optional<Car> carOptional = carRepository.findCarById(id);
        if (carOptional.isEmpty())
            throw new NotFoundException("Car does not exist for UUID: " + id + ". Please, check the entered input");
        return CarDTOMapper.INSTANCE.mapToCarDTO(carOptional.get());
    }

    @Override
    public CarDTO createCar(Car car) {
        try {
            carRepository.save(car);
            return CarDTOMapper.INSTANCE.mapToCarDTO(car);
        } catch (DataAccessException dae) {
            throw new CouldNotCreateException("Internal Error. Could not create a new car.");
        }
    }

    @Override
    public CarDTO updateCar(UUID carId, UpdateCarInfoForm car) {
        Optional<Car> existingCarOptional = carRepository.findCarById(carId);
        if (existingCarOptional.isEmpty()) {
            throw new NotFoundException("Car does not exist for UUID: " + carId);
        }

        Car existingCar = existingCarOptional.get();

        existingCar.setMark(Optional.ofNullable(car.getMark()).orElse(existingCar.getMark()));
        existingCar.setModel(Optional.ofNullable(car.getModel()).orElse(existingCar.getModel()));
        existingCar.setColor(Optional.ofNullable(car.getColor()).orElse(existingCar.getColor()));
        existingCar.setFuelType(Optional.ofNullable(car.getFuelType()).orElse(existingCar.getFuelType()));
        existingCar.setFuelEfficiency(Optional.ofNullable(car.getFuelEfficiency()).orElse(existingCar.getFuelEfficiency()));
        existingCar.setYear(Optional.ofNullable(car.getYear()).orElse(existingCar.getYear()));
        existingCar.setRegistrationNumber(Optional.ofNullable(car.getRegistrationNumber()).orElse(existingCar.getRegistrationNumber()));

        try {
            carRepository.save(existingCar);
            return CarDTOMapper.INSTANCE.mapToCarDTO(existingCar);
        } catch (DataAccessException dae) {
            throw new CouldNotUpdateException("Internal Error. Could not update the car.", dae);
        }
    }

    @Override
    public List<CarDTO> deleteCarsByUserId(UUID userId) {
        Optional<List<Car>> deletedCars = carRepository.deleteCarsByUserId(userId);
        if (deletedCars.isEmpty())
            throw new NotFoundException("User does not exist for UUID: " + userId + ". Please, check the entered input");
        return CarDTOMapper.INSTANCE.mapToCarDTOList(deletedCars.get());
    }

    @Override
    public CarDTO deleteCarById(UUID carId) {
        Optional<Car> deletedCar = carRepository.deleteCarById(carId);
        if (deletedCar.isEmpty())
            throw new NotFoundException("Car does not exist for UUID: " + carId + ". Please, check the entered input");
        return CarDTOMapper.INSTANCE.mapToCarDTO(deletedCar.get());
    }
}
