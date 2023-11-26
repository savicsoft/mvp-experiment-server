package com.savicsoft.carpooling.car.service.impl;

import com.google.cloud.storage.Blob;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.form.CreateCarForm;
import com.savicsoft.carpooling.car.model.mapper.CarDTOMapper;
import com.savicsoft.carpooling.exception.*;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.repository.CarRepository;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.car.model.form.UpdateCarInfoForm;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.security.auth.JwtUtils;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final GoogleCloudStorageService storageService;

    @Override
    public List<CarDTO> getAllCarsOfUser(UUID userId) {
        Optional<List<Car>> carsOptional = carRepository.findAllByUserId(userId);
        if (carsOptional.isEmpty())
            throw new NotFoundException("User does not exist for UUID: " + userId);
        return CarDTOMapper.INSTANCE.mapToCarDTOList(carsOptional.get());
    }

    @Override
    public CarDTO getCarById(UUID id) {
        Optional<Car> carOptional = carRepository.findCarById(id);
        if (carOptional.isEmpty())
            throw new NotFoundException("Car does not exist for UUID: " + id);
        // Car pictures
        CarDTO carDTO = CarDTOMapper.INSTANCE.mapToCarDTO(carOptional.get());
        UUID userId = carDTO.getUserId();
        List<MultipartFile> pictures = storageService.downloadFiles(userId.toString() + "/" + id.toString());
        carDTO.setPictures(pictures);
        return carDTO;
    }

    @Override
    public CarDTO createCar(String authorizationHeader, CreateCarForm carForm) {
        //get user who requested this by jwt token and set the ownership to the same user
        User user = getAuthorizedUser(authorizationHeader);

        if (user.getCars() == null)
            user.setCars(new ArrayList<>());
        if (user.getCars().size() < 3) {
            //Should this be checked: if (!user.isDriver())?
            Car newCar = Car.builder()
                    .mark(carForm.getMark())
                    .model(carForm.getModel())
                    .color(carForm.getColor())
                    .fuelType(carForm.getFuelType())
                    .fuelEfficiency(carForm.getFuelEfficiency())
                    .year(carForm.getYear())
                    .user(user)
                    .registrationNumber(carForm.getRegistrationNumber())
                    .build();
            user.getCars().add(newCar);
            userRepository.save(user);
            // Handle the pictures upload
            if (carForm.getPictures() != null) {
                for (MultipartFile picture : carForm.getPictures()) {
                    int existingPics = newCar.getNumOfPics();
                    String fullFileName = user.getId().toString() + "/" + newCar.getId().toString() + "/" + picture.getName();
                    Blob uploadedPicture = storageService.uploadFile(picture, fullFileName, newCar.getId());
                    newCar.setNumOfPics(existingPics++);
                }
            }
            try {
                carRepository.save(newCar);
                return CarDTOMapper.INSTANCE.mapToCarDTO(newCar);
            } catch (DataAccessException dae) {
                throw new CouldNotCreateException("Internal Error. Could not create a new car.");
            }
        }
        // Handle response if user already has 3 cars
        else {
            throw new MaxCarsReachedException("User is only allowed to have 3 different cars");
        }
    }

    @Override
    public CarDTO updateCar(String authorizationHeader, UUID carId, UpdateCarInfoForm car) {
        User user = getAuthorizedUser(authorizationHeader);
        Optional<Car> existingCarOptional = carRepository.findCarById(carId);
        if (existingCarOptional.isEmpty()) {
            throw new NotFoundException("Car does not exist for UUID: " + carId);
        }
        Car existingCar = existingCarOptional.get();
        if (!validateUserOwnership(user, existingCar.getId()))
            throw new CouldNotUpdateException("User does not own this car: " + carId);

        existingCar.setMark(Optional.ofNullable(car.getMark()).orElse(existingCar.getMark()));
        existingCar.setModel(Optional.ofNullable(car.getModel()).orElse(existingCar.getModel()));
        existingCar.setColor(Optional.ofNullable(car.getColor()).orElse(existingCar.getColor()));
        existingCar.setFuelType(Optional.ofNullable(car.getFuelType()).orElse(existingCar.getFuelType()));
        existingCar.setFuelEfficiency(Optional.of(car.getFuelEfficiency()).orElse(existingCar.getFuelEfficiency()));
        existingCar.setYear(Optional.of(car.getYear()).orElse(existingCar.getYear()));
        existingCar.setRegistrationNumber(Optional.ofNullable(car.getRegistrationNumber()).orElse(existingCar.getRegistrationNumber()));

        try {
            carRepository.save(existingCar);
            return CarDTOMapper.INSTANCE.mapToCarDTO(existingCar);
        } catch (DataAccessException e) {
            throw new CouldNotUpdateException("Internal Error. Could not update the car. ", e);
        }
    }

    @Override
    public List<MultipartFile> uploadPictures(String authorizationHeader, UUID carId, List<MultipartFile> pictures) {
        Optional<Car> carOptional = carRepository.findCarById(carId);
        if (carOptional.isEmpty())
            throw new NotFoundException("Car with UUID: " + carId + " does not exist.");

        User user = getAuthorizedUser(authorizationHeader);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotUpdateException("User does not own this car: " + carId);

        Car car = carOptional.get();
        if (car.getNumOfPics() + pictures.size() > 5)
            throw new CouldNotUpdateException("Maximum number of pictures for this car reached");

        // Handle the pictures upload
        for (MultipartFile picture : pictures) {
            String fullFileName = user.getId().toString() + "/" + carId.toString() + "/" + picture.getName();
            Blob uploadedPicture = storageService.uploadFile(picture, fullFileName, carId);
            car.setNumOfPics(car.getNumOfPics()+1);
        }
        try {
            carRepository.save(car);
            return pictures;
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not update the car after uploading pictures.", e);
        }
    }

    @Override
    public boolean deletePictures(String authorizationHeader, UUID carId, List<String> fileNames) {
        Optional<Car> carOptional = carRepository.findCarById(carId);
        if (carOptional.isEmpty())
            throw new NotFoundException("Car with UUID: " + carId + " does not exist.");

        User user = getAuthorizedUser(authorizationHeader);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotDeleteException("User does not own this car: " + carId);

        Car car = carOptional.get();
        for (String fileName : fileNames) {
            String fullFileName = user.getId().toString() + "/" + carId.toString() + "/" + fileName;
            storageService.deleteFile(fullFileName);
            car.setNumOfPics(car.getNumOfPics() - 1);
        }

        try {
            carRepository.save(car);
            return true;
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not update the car after deleting pictures.", e);
        }
    }

    @Override
    public boolean deleteAllUserCars(String authorizationHeader) {
        User user = getAuthorizedUser(authorizationHeader);
        try {
            carRepository.deleteCarsByUserId(user.getId());
            return carRepository.findAllByUserId(user.getId()).isEmpty();
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not delete cars. ", e);
        }
    }

    @Override
    public boolean deleteCarById(String authorizationHeader, UUID carId) {
        User user = getAuthorizedUser(authorizationHeader);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotDeleteException("User does not own this car: " + carId);
        try {
            carRepository.deleteCarById(carId);
            return carRepository.findCarById(carId).isEmpty();
        } catch (DataAccessException e) {
            throw new CouldNotDeleteException("Internal Error. Could not delete a car. ", e);
        }
    }

    private User getAuthorizedUser(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization Header must start with 'Bearer'");
        }
        String jwtToken = authorizationHeader.substring(7);
        String userEmail = jwtUtils.getUserNameFromJwtToken(jwtToken);
        Optional<User> user = userRepository.getUserByEmail(userEmail);
        if (user.isEmpty())
            throw new NotFoundException("Username does not exist: " + userEmail);
        return user.get();
    }

    private boolean validateUserOwnership(User user, UUID carId) {
        return user.getCars().stream().anyMatch(car -> car.getId().equals(carId));
    }
}
