package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.ride.model.dto.PassengerPlannedRidesDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import com.savicsoft.carpooling.user.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@SpringBootTest
@TestPropertySource("/application.properties")
public class PassengerPlannedRidesTest {
    @Test
    public void testPassengerRideToPassengerPlannedRidesDTO() {
        User passenger = new User();
        passenger.setId(UUID.randomUUID());
        passenger.setFirstName("Alice");
        passenger.setLastName("Smith");

        User driver = new User();
        driver.setId(UUID.randomUUID());
        driver.setFirstName("John");
        driver.setLastName("Doe");

        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setRegistrationNumber("ABC123");
        car.setColor("Blue");
        car.setYear(2020);

        DriverRide driverRide = new DriverRide(UUID.randomUUID(), driver, car, "Start", "End", (short) 3,
                LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        PassengerRide passengerRide = new PassengerRide(UUID.randomUUID(), passenger, driverRide, "PassengerStart", "PassengerEnd",
                LocalDateTime.now().plusMinutes(30), RideStatus.Requested, new BigDecimal("20.00"));

        PassengerPlannedRidesDTO dto = PassengerPlannedRidesMapper.INSTANCE.passengerRideToPassengerPlannedRidesDTO(passengerRide);
        toDtoChecker(passengerRide, dto);

    }
    @Test
    public void testPassengerRideToPassengerPlannedRidesDTOs() {
        User passenger1 = new User();
        passenger1.setId(UUID.randomUUID());
        passenger1.setFirstName("Alice");
        passenger1.setLastName("Smith");

        User passenger2 = new User();
        passenger2.setId(UUID.randomUUID());
        passenger2.setFirstName("Bob");
        passenger2.setLastName("Johnson");

        User driver = new User();
        driver.setId(UUID.randomUUID());
        driver.setFirstName("John");
        driver.setLastName("Doe");

        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setRegistrationNumber("ABC123");
        car.setColor("Blue");
        car.setYear(2020);

        DriverRide driverRide1 = new DriverRide(UUID.randomUUID(), driver, car, "Start1", "End1", (short) 3,
                LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        DriverRide driverRide2 = new DriverRide(UUID.randomUUID(), driver, car, "Start2", "End2", (short) 4,
                LocalDateTime.now(), LocalDateTime.now().plusHours(3));

        PassengerRide passengerRide1 = new PassengerRide(UUID.randomUUID(), passenger1, driverRide1, "PassengerStart1", "PassengerEnd1",
                LocalDateTime.now().plusMinutes(30), RideStatus.Requested, new BigDecimal("20.00"));

        PassengerRide passengerRide2 = new PassengerRide(UUID.randomUUID(), passenger2, driverRide2, "PassengerStart2", "PassengerEnd2",
                LocalDateTime.now().plusMinutes(45), RideStatus.Requested, new BigDecimal("25.00"));

        List<PassengerRide> passengerRides = new ArrayList<>();
        passengerRides.add(passengerRide1);
        passengerRides.add(passengerRide2);
        List<PassengerPlannedRidesDTO> dtos = PassengerPlannedRidesMapper.INSTANCE.passengerRideToPassengerPlannedRidesDTOs(passengerRides);
        for(int i=0; i<passengerRides.size(); i++){
            toDtoChecker(passengerRides.get(i), dtos.get(i));
        }
    }

    public void toDtoChecker(PassengerRide passengerRide, PassengerPlannedRidesDTO dto){
       assertEquals(passengerRide.getId(), dto.getId());
        assertEquals(passengerRide.getDriverRide().getId(), dto.getDriverRideId());
        assertEquals(passengerRide.getDriverRide().getDriver().getId(), dto.getDriverId());
        assertEquals(passengerRide.getDriverRide().getDriver().getFirstName(), dto.getDriverFirstName());
        assertEquals(passengerRide.getDriverRide().getDriver().getLastName(), dto.getDriverLastName());
        assertEquals(passengerRide.getDriverRide().getCar().getId(), dto.getCarId());
        assertEquals(passengerRide.getDriverRide().getCar().getRegistrationNumber(), dto.getCarRegistrationNumber());
        assertEquals(passengerRide.getDriverRide().getCar().getColor(), dto.getCarColor());
        assertEquals(passengerRide.getDriverRide().getCar().getYear(), dto.getCarYearOfManufacture());
        assertEquals(passengerRide.getStartingPoint(), dto.getStartingPoint());
        assertEquals(passengerRide.getEndingPoint(), dto.getEndingPoint());
        assertEquals(passengerRide.getPickUpTime(), dto.getPickUpTime());
        assertEquals(passengerRide.getRidePrice(), dto.getRidePrice());
        assertEquals(passengerRide.getRideStatus(), dto.getRideStatus());
    }
}
