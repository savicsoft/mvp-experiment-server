package com.savicsoft.carpooling.car.service.impl;

import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.mapper.CarMapper;
import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.exception.CouldNotCreateException;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
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
        Optional<List<Car>> carsOptional = carRepository.findAllByUserUuid(uuid);
        if (carsOptional.isEmpty())
            throw new UserNotFoundException("User does not exist for UUID: " + uuid + ". Please, check the entered input");
        return CarMapper.INSTANCE.mapToCarDTOList(carsOptional.get());
    }

    @Override
    public List<CarDTO> getAllCars() {
        return CarMapper.INSTANCE.mapToCarDTOList(carRepository.findAll());
    }

    @Override
    public void deleteCarsByUserId(Long id) {
        carRepository.deleteCarsByUserId(id);
    }

    @Override
    public CarDTO createCar(Car car) {
        try {
            carRepository.save(car);
            return CarMapper.INSTANCE.mapToCarDTO(car);
        } catch (DataAccessException dae) {
            throw new CouldNotCreateException("Internal Error. Could not create a new car.");
        }
    }

    @Override
    public CarDTO getCarByUUID(UUID uuid) {
        Optional<Car> carOptional = carRepository.findCarByUuid(uuid);
        if (carOptional.isEmpty())
            throw new NotFoundException("Car does not exist for UUID: " + uuid + ". Please, check the entered input");
        return CarMapper.INSTANCE.mapToCarDTO(carOptional.get());
    }
}
