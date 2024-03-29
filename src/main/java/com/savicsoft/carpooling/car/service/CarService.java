package com.savicsoft.carpooling.car.service;

import com.savicsoft.carpooling.car.model.form.CreateCarForm;
import com.savicsoft.carpooling.car.model.form.UpdateCarInfoForm;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarDTO> getAllCarsOfUser(UUID uuid);
    CarDTO getCarById(UUID uuid);
    CarDTO createCar(CreateCarForm carForm);
    CarDTO updateCar(UUID carId, UpdateCarInfoForm car);
    List<String> uploadPictures(UUID carId, List<MultipartFile> pictures);
    boolean deletePictures(UUID carId, List<String> fileNames);
    boolean deleteAllUserCars();
    boolean deleteCarById(UUID carId);
}
