package com.savicsoft.carpooling.car.controller;

import com.google.cloud.storage.Blob;
import com.savicsoft.carpooling.car.model.form.CreateCarForm;
import com.savicsoft.carpooling.car.model.form.UpdateCarInfoForm;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.exception.CouldNotDeleteException;
import com.savicsoft.carpooling.exception.CouldNotUpdateException;
import com.savicsoft.carpooling.exception.MaxCarsReachedException;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.security.auth.JwtUtils;
import com.savicsoft.carpooling.user.model.dto.UserDTO;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.model.mapper.UserMapper;
import com.savicsoft.carpooling.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Tag(
        name = "CRUD REST APIs for user cars",
        description = "Operations to interact user cars"
)
public class CarController {

    private final CarService carService;
    private final UserService userService;
    private final GoogleCloudStorageService storageService;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "Retrieve all cars owned by one user",
            description = "Retrieve all cars that belong to User by his UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<CarDTO>> getAllUserCars(@PathVariable UUID userId){
        // Cars info
        List<CarDTO> cars =  carService.getAllCarsOfUser(userId);
        // Is there a need to download pics for all cars here?
        return ResponseEntity.ok(cars);
    }

    @Operation(
            summary = "Retrieve specified car",
            description = "Retrieve specified car by Car UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> getUserCarById(@PathVariable UUID carId) {
        // Car info
        CarDTO carDTO = carService.getCarById(carId);
        // Car pictures
        UUID userId = carDTO.getUserId();
        List<MultipartFile> pictures = storageService.downloadFiles(userId.toString() + "/" + carId.toString());
        carDTO.setPictures(pictures);
        return ResponseEntity.ok(carDTO);
    }

    @Operation(
            summary = "Create a new car",
            description = "Create a new car and set the ownership to the user that sent request"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status: 200 -> Created"
    )
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDTO> createCar(@RequestHeader("Authorization") String authorizationHeader,
                                            @Valid CreateCarForm car) {

        //get user who requested this by jwt token and set the ownership to the same user
        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
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
                    String fullFileName = user.getId().toString() + "/" + newCar.getId().toString() + "/" + picture.getName();
                    Blob uploadedPicture = storageService.uploadFile(picture, fullFileName, newCar.getId());
                    newCar.setNumOfPics(existingPics++);
                }
            }
            CarDTO newCarDTO = carService.createCar(newCar);
            return new ResponseEntity<>(newCarDTO, HttpStatus.CREATED);
        }
        // Handle response if user already has 3 cars
        else {
            throw new MaxCarsReachedException("User is only allowed to have 3 different cars");
        }
    }

    @Operation(
            summary = "Update info of an existing car",
            description = "Update car info by car UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Created"
    )
    @PatchMapping("/updateInfo/{carId}")
    public ResponseEntity<CarDTO> updateCarInfo(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable UUID carId,
                                                UpdateCarInfoForm car){

        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        CarDTO existingCar = carService.getCarById(carId);
        if (!validateUserOwnership(user, existingCar.getId()))
            throw new CouldNotUpdateException("User does not own this car: " + carId.toString());
        CarDTO updatedCar = carService.updateCar(carId, car);

        return ResponseEntity.ok(updatedCar);
    }

    @Operation(
            summary = "Upload car pictures only",
            description = "Upload pictures of a car specified by Car UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @PostMapping(value = "/{carId}/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDTO> uploadCarPictures(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable UUID carId,
                                                    @RequestParam List<MultipartFile> pictures) {

        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotUpdateException("User does not own this car: " + carId.toString());

        CarDTO carDTO = carService.getCarById(carId);
        if (carDTO.getPictures() != null && pictures.size()+carDTO.getPictures().size() > 5)
            throw new CouldNotUpdateException("Maximum number of pictures for this car reached");

        // Handle the pictures upload
        for (MultipartFile picture : pictures) {
            String fullFileName = user.getId().toString() + "/" + carId.toString() + "/" + picture.getName();
            Blob uploadedPicture = storageService.uploadFile(picture, fullFileName, carId);
            carDTO.getPictures().add(picture);
            // TODO: convert DTO to car, increment numOfPics and update carInfo
        }

        return ResponseEntity.ok(carDTO);
    }

    //TODO: endpoint to deal with car pictures updates

    @Operation(
            summary = "Delete car pictures only",
            description = "Delete pictures of a car specified by Car UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @DeleteMapping(value = "/{carId}/pictures")
    public ResponseEntity<CarDTO> deleteCarPictures(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable UUID carId,
                                                    @RequestParam("file names") List<String> fileNames) {

        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotDeleteException("User does not own this car: " + carId.toString());

        CarDTO carDTO = carService.getCarById(carId);

        for (String fileName : fileNames) {
            String fullFileName = user.getId().toString() + "/" + carId.toString() + "/" + fileName;
            storageService.deleteFile(fullFileName);
            List<MultipartFile> pictures = carDTO.getPictures();
            for (MultipartFile picture : pictures) {
                if (picture.getName().equals(fileName))
                    pictures.remove(picture);
            }
            // TODO: convert DTO to car, decrement numOfPics and update carInfo
        }

        return ResponseEntity.ok(carDTO);
    }

    @Operation(
            summary = "Delete all cars owned by one user",
            description = "Delete all cars that belong to User by his UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<List<CarDTO>> deleteCars(@RequestHeader("Authorization") String authorizationHeader) {

        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
        List<CarDTO> deletedCars = carService.deleteCarsByUserId(userDTO.getId());

        return ResponseEntity.ok(deletedCars);
    }

    @Operation(
            summary = "Delete specified car",
            description = "Delete specified car by Car UUID id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<CarDTO> deleteCar(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable UUID carId) {

        UserDTO userDTO = getAuthorizedUserDTO(authorizationHeader);
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        if (!validateUserOwnership(user, carId))
            throw new CouldNotDeleteException("User does not own this car: " + carId.toString());
        CarDTO deletedCar = carService.deleteCarById(carId);

        return ResponseEntity.ok(deletedCar);
    }

    private UserDTO getAuthorizedUserDTO(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization Header must start with 'Bearer'");
        }
        String jwtToken = authorizationHeader.substring(7);
        String userEmail = jwtUtils.getUserNameFromJwtToken(jwtToken);
        UserDTO userDTO = userService.getUserByEmail(userEmail);
        return userDTO;
    }

    private boolean validateUserOwnership(User user, UUID carId) {
        return carService.getCarById(carId).getUserId().equals(user.getId());
    }
}
