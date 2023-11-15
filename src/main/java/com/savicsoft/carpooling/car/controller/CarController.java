package com.savicsoft.carpooling.car.controller;

import com.google.cloud.storage.Blob;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.exception.MaxCarsReachedException;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.security.auth.JwtUtils;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import com.savicsoft.carpooling.user.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

   private final CarService carService;
   private final UserService userService;
   private final GoogleCloudStorageService storageService;
   private final JwtUtils jwtUtils;

   @GetMapping("/{userId}")
   public ResponseEntity<List<CarDTO>> getAllUserCars(@PathVariable UUID userId){
      // Cars info
      List<CarDTO> cars =  carService.getAllCarsOfUser(userId);
      // Is there a need to download pics for all cars here?
      return ResponseEntity.ok(cars);
   }

   @GetMapping("/{carId}")
   public ResponseEntity<CarDTO> getUserCarByUuid(@PathVariable UUID carId) {
      // Car info
      CarDTO carDTO = carService.getCarByUUID(carId);
      // Car pictures
      UUID userUuid = carDTO.getUserId();
      List<MultipartFile> pictures = storageService.downloadFiles(userUuid.toString() + "/" + carId.toString());
      carDTO.setPictures(pictures);
      return ResponseEntity.ok(carDTO);
   }

   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   @Transactional
   public ResponseEntity<?> createCar(
           @RequestHeader("Authorization") String authorizationHeader,
           @Valid CarDTO car) {

      //get user who requested this by jwt token and set the ownership to the same user
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
         throw new IllegalArgumentException("Authorization Header must start with 'Bearer'");
      }
      String jwtToken = authorizationHeader.substring(7);
      String userEmail = jwtUtils.getUserNameFromJwtToken(jwtToken);
      UserDTO userDTO = userService.getUserByEmail(userEmail);
      User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

      if(userDTO.getCarsCount() < 3) {
         //Should this be checked: if (!user.isDriver())?
         Car newCar = Car.builder()
                 .mark(car.getMark())
                 .model(car.getModel())
                 .color(car.getColor())
                 .fuelType(car.getFuelType())
                 .fuelEfficiency(car.getFuelEfficiency())
                 .year(car.getYear())
                 .user(user)
                 .registrationNumber(car.getRegistrationNumber())
                 .build();
         userDTO.setCarsCount(userDTO.getCarsCount()+1);

         // Handle the pictures upload
         if (car.getPictures() != null) {
            for (MultipartFile picture : car.getPictures()) {
               int existingPics = newCar.getNumOfPics();
               String fullFileName = user.getUuid().toString() + "/" + newCar.getUuid().toString() + "/" + picture.getName();
               Blob uploadedPicture = storageService.uploadFile(picture, fullFileName, newCar.getUuid());
               newCar.setNumOfPics(existingPics++);
            }
         }
         CarDTO newCarDTO = carService.createCar(newCar);
         return ResponseEntity.ok(newCarDTO);
      }
      // Handle response if user already has 3 cars
      else {
         throw new MaxCarsReachedException("User is only allowed to have 3 different cars");
      }
   }
}
