package com.savicsoft.carpooling.car.controller;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.car.enumeration.FuelType;
import com.savicsoft.carpooling.car.forms.CarForm;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.domain.HttpResponse;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
   // private final UserService userService;

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
        user.setId(1l);
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


}
