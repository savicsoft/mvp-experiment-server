package com.savicsoft.carpooling.car.controller;

import com.google.cloud.storage.Blob;
import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.enumeration.FuelType;
import com.savicsoft.carpooling.car.forms.CarForm;
import com.savicsoft.carpooling.domain.ImageUploadForm;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.domain.HttpResponse;
import com.savicsoft.carpooling.googlecloudstorage.service.GoogleCloudStorageService;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
   // private final UserService userService;
   private final GoogleCloudStorageService storageService;

    //TODO: This will be for testing and should be enabled for admins only
    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getAllCars(){
        List<CarDTO> cars =  carService.getAllCars();
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("cars",cars))
                        .message("All cars are retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }
    @GetMapping("/list/user")
    public ResponseEntity<HttpResponse> getUsersCars(@RequestBody User user){
        List<CarDTO> cars =  carService.getAllUsersCars(user.getId());
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("cars",cars))
                        .message("User cars are retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getCarById(@RequestParam("id") Long id){
        CarDTO car =  carService.getCarById(id);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("car",car))
                        .message("Car retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HttpResponse> getCarByUUID(@RequestParam("uuid") UUID uuid){
        CarDTO car =  carService.getCarByUUID(uuid);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("car",car))
                        .message("Car retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/{regnum}")
    public ResponseEntity<HttpResponse> getCarByRegistrationNumber(@RequestParam("regnum") Long regnum){
        CarDTO car =  carService.getCarById(regnum);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("car",car))
                        .message("Car retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteCar(@RequestParam("id") Long id){
        CarDTO car =  carService.getCarById(id);
        carService.deleteById(id);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("car",car))
                        .message("Car Deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createCar(
            @RequestBody @Valid CarForm car) throws InterruptedException {
        //TODO: Add get user by UUID and set the ownership to the same user
        User user = new User();
        user.setId(1L);
        UUID newUUID = UUID.randomUUID();
//        while (carService.getCarByUUID(newUUID)!=null){
//            newUUID = UUID.randomUUID();
//        }

        Car newCar = Car.builder()
                .color(car.getColor())
                .fuelType(FuelType.valueOf(car.getFuelType()))
                .fuelEfficiency(car.getFuelEfficiency())
                .year(car.getYear())
                .user(user)
                .pictureUrl(car.getPictureUrl())
                .uuid(newUUID)
                .registrationNumber(car.getRegistrationNumber())
                .build();
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("car", carService.createCar(newCar)))
                                .message("Car created")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build());
    }

    @PostMapping("/{carUUID}/upload-image")
    public ResponseEntity<HttpResponse> uploadCarPicture(@PathVariable UUID carUUID, @RequestBody ImageUploadForm carImageForm) {
        int existingPictures = carService.getCarByUUID(carUUID).getPictureUrl().size();

        if (existingPictures >= 5) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("error", "Maximum number of pictures (5) reached for this car."))
                            .message("Maximum number of pictures reached")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
        try {
            UUID userUUID = carService.getCarByUUID(carUUID).getUser().getUuid();
            String fullFileName = userUUID.toString() + "/" + carUUID.toString() + "/" + carImageForm.getFileName();

            Blob uploadedPicture = storageService.uploadFile(carImageForm.getPicture(), fullFileName, carUUID);
            carService.getCarByUUID(carUUID).getPictureUrl().add("https://storage.googleapis.com/" + uploadedPicture.getName());

            return ResponseEntity.ok(
                    HttpResponse.builder()
                               .timeStamp(LocalDateTime.now().toString())
                               .data(Map.of("picture", uploadedPicture))
                               .message("Car picture uploaded successfully")
                               .status(HttpStatus.OK)
                               .statusCode(HttpStatus.OK.value())
                               .build()
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .data(Map.of("error", "Failed to upload car picture: " + e.getMessage()))
                                    .message("Failed to upload car picture")
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .build()
                    );
        }
    }
}
