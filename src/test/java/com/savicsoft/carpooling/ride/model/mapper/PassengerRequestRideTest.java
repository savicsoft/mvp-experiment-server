package com.savicsoft.carpooling.ride.model.mapper;


import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.ride.model.dto.PassengerRequestToDriverDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.ride.model.entity.PassengerRide;
import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import com.savicsoft.carpooling.user.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application.properties")
public class PassengerRequestRideTest {
    @Test
    public void testPassengerRideToPassengerRequestToDriverDTO() {
        // Create sample User instance for passenger
        User passenger = new User();
        passenger.setId(UUID.randomUUID());
        passenger.setFirstName("Alice");
        passenger.setLastName("Smith");

        // Create sample DriverRide instance for driverRide
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

        Short currentPassengerAmount = 2;
        PassengerRequestToDriverDTO dto = PassengerRequestRideMapper.INSTANCE.passengerRideToPassengerRequestRideDTO(passengerRide, currentPassengerAmount);
        toDtoChecker(passengerRide, dto, currentPassengerAmount);

    }
    @Test
    public void testPassengerRideToPassengerRequestToDriverDTOs() {
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

        Map<PassengerRide, Short> passengerRidesWithAmounts = new LinkedHashMap<>();
        Short currentPassengersAmount1 = 2;
        Short currentPassengersAmount2 = 3;
        passengerRidesWithAmounts.put(passengerRide1, currentPassengersAmount1);
        passengerRidesWithAmounts.put(passengerRide2, currentPassengersAmount2);
        List<PassengerRequestToDriverDTO> dtos = PassengerRequestRideMapper.INSTANCE.passengerRideToPassengerRequestRideDTOs(passengerRidesWithAmounts);
        int i = 0;
        for (Map.Entry<PassengerRide, Short> entry : passengerRidesWithAmounts.entrySet()) {
            PassengerRide passengerRide = entry.getKey();
            Short currentPassengersAmount = entry.getValue();
            PassengerRequestToDriverDTO dto = dtos.get(i);
            toDtoChecker(passengerRide, dto, currentPassengersAmount);
            i++;
        }
    }

    public void toDtoChecker(PassengerRide passengerRide, PassengerRequestToDriverDTO dto, Short currentPassengerAmount){
        assertEquals(passengerRide.getId(), dto.getId());
        assertEquals(passengerRide.getPassenger().getId(), dto.getPassengerId());
        assertEquals(passengerRide.getPassenger().getFirstName(), dto.getPassengerFirstName());
        assertEquals(passengerRide.getPassenger().getLastName(), dto.getPassengerLastName());
        assertEquals(passengerRide.getDriverRide().getId(), dto.getDriverRideId());
        assertEquals(passengerRide.getDriverRide().getStartingPoint(), dto.getRideStartingPoint());
        assertEquals(passengerRide.getDriverRide().getEndingPoint(), dto.getRideEndingPoint());
        assertEquals(passengerRide.getDriverRide().getPassengersLimit(), dto.getPassengersLimit());
        assertEquals(currentPassengerAmount, dto.getCurrentPassengersAmount());
        assertEquals(passengerRide.getDriverRide().getStartingTime(), dto.getRideStartingTime());
        assertEquals(passengerRide.getDriverRide().getEndingTime(), dto.getRideEndingTime());
        assertEquals(passengerRide.getStartingPoint(), dto.getPassengerStartingPoint());
        assertEquals(passengerRide.getEndingPoint(), dto.getPassengerEndingPoint());
        assertEquals(passengerRide.getPickUpTime(), dto.getPickUpTime());
        assertEquals(passengerRide.getRidePrice(), dto.getRidePrice());
    }

}
