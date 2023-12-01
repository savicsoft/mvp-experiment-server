package com.savicsoft.carpooling.car.controller;

import com.savicsoft.carpooling.car.model.form.CreateCarForm;
import com.savicsoft.carpooling.car.model.form.UpdateCarInfoForm;
import com.savicsoft.carpooling.car.model.dto.CarDTO;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.exception.errorinfo.ErrorInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        name = "Car resource",
        description = "Operations to interact user cars"
)
public class CarController {

    private final CarService carService;

    @Operation(
            summary = "Retrieve all cars owned by one user",
            description = "Retrieve all cars that belong to a user by their UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CarDTO>> getAllUserCars(@PathVariable UUID userId) {
        List<CarDTO> cars = carService.getAllCarsOfUser(userId);
        return ResponseEntity.ok(cars);
    }

    @Operation(
            summary = "Retrieve specified car",
            description = "Retrieve a specified car by its UUID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Car pictures cannot be retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> getUserCarById(@PathVariable UUID carId) {
        CarDTO carDTO = carService.getCarById(carId);
        return ResponseEntity.ok(carDTO);
    }

    @Operation(
            summary = "Create a new car",
            description = "Create a new car with the provided information and pictures."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Car created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. The user is not allowed to create more cars.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Car could not be created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDTO> createCar(@RequestHeader("Authorization") String authorizationHeader,
                                            @Valid CreateCarForm carForm) {

        CarDTO newCarDTO = carService.createCar(authorizationHeader, carForm);
        return new ResponseEntity<>(newCarDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update info of an existing car",
            description = "Update car info by car UUID id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Car info updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, user does not own a car",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Car could not be updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @PatchMapping("/updateInfo/{carId}")
    public ResponseEntity<CarDTO> updateCarInfo(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable UUID carId,
                                                UpdateCarInfoForm car){

        CarDTO updatedCar = carService.updateCar(authorizationHeader, carId, car);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(
            summary = "Upload car pictures",
            description = "Upload pictures for a specified car."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Car pictures uploaded successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data received",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, user does not own a car",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Maximum number of pictures reached",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Car pictures could not be uploaded",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @PostMapping(value = "/{carId}/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadCarPictures(@RequestHeader("Authorization") String authorizationHeader,
                                                                 @PathVariable UUID carId,
                                                                 @RequestParam List<MultipartFile> pictures) {

        List<String> uploadedPictures = carService.uploadPictures(authorizationHeader, carId, pictures);
        return ResponseEntity.ok(uploadedPictures);
    }

    @Operation(
            summary = "Delete car pictures only",
            description = "Delete pictures of a car specified by Car UUID id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Car pictures deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data received",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, user does not own a car",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Car pictures could not be deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @DeleteMapping(value = "/{carId}/pictures")
    public ResponseEntity<Boolean> deleteCarPictures(@RequestHeader("Authorization") String authorizationHeader,
                                                     @PathVariable UUID carId,
                                                     @RequestParam("file names") List<String> fileNames) {

        Boolean deletedPictures = carService.deletePictures(authorizationHeader, carId, fileNames);
        return ResponseEntity.ok(deletedPictures);
    }

    @Operation(
            summary = "Delete all cars owned by one user",
            description = "Delete all cars that belong to User by his UUID id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cars deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Cars could not be deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteCars(@RequestHeader("Authorization") String authorizationHeader) {

        Boolean deletedCars = carService.deleteAllUserCars(authorizationHeader);
        return ResponseEntity.ok(deletedCars);
    }

    @Operation(
            summary = "Delete specified car",
            description = "Delete specified car by Car UUID id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Car deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, user does not own a car",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Car could not be deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
            )
    })
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<Boolean> deleteCar(@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable UUID carId) {

        Boolean deletedCar = carService.deleteCarById(authorizationHeader, carId);
        return ResponseEntity.ok(deletedCar);
    }
}
