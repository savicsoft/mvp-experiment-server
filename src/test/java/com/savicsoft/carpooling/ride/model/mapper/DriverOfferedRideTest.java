package com.savicsoft.carpooling.ride.model.mapper;


import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.ride.model.dto.DriverOfferedRideDTO;
import com.savicsoft.carpooling.ride.model.entity.DriverRide;
import com.savicsoft.carpooling.user.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@TestPropertySource("/application.properties")
public class DriverOfferedRideTest {
    @Test
    public void testDriverRideToDriverOfferedRideDTO() {
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

        BigDecimal calculatedPrice = new BigDecimal("50.00");
        DriverOfferedRideDTO dto = DriverOfferedRideMapper.INSTANCE.driverRideToDriverOfferedRideDTO(driverRide, calculatedPrice);
        toDtoChecker(driverRide, dto, calculatedPrice);
    }

    @Test
    public void testDriverRidesToDriverOfferedRideDTOs() {
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

        BigDecimal calculatedPrice1 = new BigDecimal("50.00");
        BigDecimal calculatedPrice2 = new BigDecimal("60.00");

        Map<DriverRide, BigDecimal> driverRidesWithPrices = new LinkedHashMap<>();
        driverRidesWithPrices.put(driverRide1, calculatedPrice1);
        driverRidesWithPrices.put(driverRide2, calculatedPrice2);
        List<DriverOfferedRideDTO> dtos = DriverOfferedRideMapper.INSTANCE.driverRidesToDriverOfferedRideDTOs(driverRidesWithPrices);
        int i = 0;
        for (Map.Entry<DriverRide, BigDecimal> entry : driverRidesWithPrices.entrySet()) {
            DriverRide driverRide = entry.getKey();
            BigDecimal calculatedPrice = entry.getValue();
            DriverOfferedRideDTO dto = dtos.get(i);
            toDtoChecker(driverRide, dto, calculatedPrice);
            i++;
        }
    }

    public void toDtoChecker(DriverRide driverRide, DriverOfferedRideDTO dto, BigDecimal calculatedPrice){
        assertEquals(driverRide.getId(), dto.getId());
        assertEquals(driverRide.getDriver().getId(), dto.getDriverId());
        assertEquals(driverRide.getDriver().getFirstName(), dto.getDriverFirstName());
        assertEquals(driverRide.getDriver().getLastName(), dto.getDriverLastName());
        assertEquals(driverRide.getCar().getId(), dto.getCarId());
        assertEquals(driverRide.getCar().getRegistrationNumber(), dto.getCarRegistrationNumber());
        assertEquals(driverRide.getCar().getColor(), dto.getCarColor());
        assertEquals(driverRide.getCar().getYear(), dto.getCarYearOfManufacture());
        assertEquals(driverRide.getStartingPoint(), dto.getStartingPoint());
        assertEquals(driverRide.getEndingPoint(), dto.getEndingPoint());
        assertEquals(driverRide.getPassengersLimit(), dto.getPassengersLimit());
        assertEquals(driverRide.getStartingTime(), dto.getStartingTime());
        assertEquals(driverRide.getEndingTime(), dto.getEndingTime());
        assertEquals(calculatedPrice, dto.getCalculatedPriceForPassenger());
    }
}
