package com.savicsoft.carpooling.ride.model.mapper;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.ride.model.dto.DriverPlannedRidesDTO;
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
public class DriverPlannedRidesTest {

    @Test
    public void testDriverRideToDriverPlannedRidesDTO() {
        User driver = new User();
        driver.setId(UUID.randomUUID());
        driver.setFirstName("John");
        driver.setLastName("Doe");
        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setRegistrationNumber("ABC123");
        DriverRide driverRide = new DriverRide(UUID.randomUUID(), driver, car, "Start", "End", (short) 3,
                LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        List<PassengerRide> passengerRides = new ArrayList<>();
        passengerRides.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide, "PassengerStart1", "PassengerEnd1",
                LocalDateTime.now().plusMinutes(30), RideStatus.Accepted, new BigDecimal("20.00")));
        passengerRides.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide, "PassengerStart2", "PassengerEnd2",
                LocalDateTime.now().plusMinutes(45), RideStatus.Accepted, new BigDecimal("25.00")));
        DriverPlannedRidesDTO dto = DriverPlannedRidesMapper.INSTANCE.driverRideToDriverPlannedRidesDTO(driverRide, passengerRides);
        toDtoCheck(driverRide, passengerRides, dto);
    }

    @Test
    public void testDriverRideToDriverPlannedRidesDTOs() {
        User driver = new User();
        driver.setId(UUID.randomUUID());
        driver.setFirstName("John");
        driver.setLastName("Doe");
        Car car = new Car();
        car.setId(UUID.randomUUID());
        car.setRegistrationNumber("ABC123");
        DriverRide driverRide1 = new DriverRide(UUID.randomUUID(), driver, car, "Start", "End", (short) 3,
                LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        List<PassengerRide> passengerRides1 = new ArrayList<>();
        passengerRides1.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide1, "PassengerStart1", "PassengerEnd1",
                LocalDateTime.now().plusMinutes(30), RideStatus.Accepted, new BigDecimal("20.00")));
        passengerRides1.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide1, "PassengerStart2", "PassengerEnd2",
                LocalDateTime.now().plusMinutes(45), RideStatus.Accepted, new BigDecimal("25.00")));


        DriverRide driverRide2 = new DriverRide(UUID.randomUUID(), driver, car, "StartingPoint", "EndingPoint", (short) 5,
                LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(8));
        List<PassengerRide> passengerRides2 = new ArrayList<>();

        passengerRides2.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide2, "PassengerStart1234", "PassengerEnd1234",
                LocalDateTime.now().plusHours(6), RideStatus.Accepted, new BigDecimal("26.50")));
        passengerRides2.add(new PassengerRide(UUID.randomUUID(), new User(), driverRide2, "PassengerStart2456", "PassengerEnd22334",
                LocalDateTime.now().plusHours(7), RideStatus.Accepted, new BigDecimal("35.60")));


        Map<DriverRide, List<PassengerRide>> driverRidesWithPassengers = new LinkedHashMap<>();
        driverRidesWithPassengers.put(driverRide1, passengerRides1);
        driverRidesWithPassengers.put(driverRide2, passengerRides2);

        List<DriverPlannedRidesDTO> dtos = DriverPlannedRidesMapper.INSTANCE.driverRideToDriverPlannedRidesDTOs(driverRidesWithPassengers);
        int i = 0;
        for (Map.Entry<DriverRide, List<PassengerRide>> entry : driverRidesWithPassengers.entrySet()) {
            DriverRide driverRide = entry.getKey();
            List<PassengerRide> passengers = entry.getValue();
            DriverPlannedRidesDTO dto = dtos.get(i);
            toDtoCheck(driverRide,passengers, dto);
            i++;
        }
    }

    public void toDtoCheck(DriverRide driverRide, List<PassengerRide> passengerRides, DriverPlannedRidesDTO dto){
        assertEquals(driverRide.getId(), dto.getId());
        assertEquals(driverRide.getCar().getId(), dto.getCarId());
        assertEquals(driverRide.getCar().getRegistrationNumber(), dto.getCarRegistrationNumber());
        assertEquals(driverRide.getStartingPoint(), dto.getStartingPoint());
        assertEquals(driverRide.getEndingPoint(), dto.getEndingPoint());
        assertEquals(driverRide.getPassengersLimit(), dto.getPassengersLimit());
        assertEquals(driverRide.getStartingTime(), dto.getStartingTime());
        assertEquals(driverRide.getEndingTime(), dto.getEndingTime());
        for(int i=0; i<passengerRides.size(); i++){
            assertEquals(passengerRides.get(i).getId(), dto.getPassengers().get(i).getId());
            assertEquals(passengerRides.get(i).getPassenger().getId(), dto.getPassengers().get(i).getPassengerId());
            assertEquals(passengerRides.get(i).getPassenger().getFirstName(), dto.getPassengers().get(i).getPassengerFirstName());
            assertEquals(passengerRides.get(i).getPassenger().getLastName(), dto.getPassengers().get(i).getPassengerLastName());
            assertEquals(passengerRides.get(i).getStartingPoint(), dto.getPassengers().get(i).getStartingPoint());
            assertEquals(passengerRides.get(i).getEndingPoint(), dto.getPassengers().get(i).getEndingPoint());
            assertEquals(passengerRides.get(i).getPickUpTime(), dto.getPassengers().get(i).getPickUpTime());
            assertEquals(passengerRides.get(i).getRidePrice(), dto.getPassengers().get(i).getRidePrice());
            assertEquals(passengerRides.get(i).getRideStatus(), dto.getPassengers().get(i).getRideStatus());
        }

    }
}
