package com.savicsoft.carpooling.ride.model.entity;

import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "driver_ride")
public class DriverRide {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name = "ending_point")
    private String endingPoint;

    @Column(name = "passengers_limit")
    private Short passengersLimit;

    @Column(name = "starting_time")
    private LocalDateTime startingTime;

    @Column(name = "ending_time")
    private LocalDateTime endingTime;

    public DriverRide(User driver, Car car, String startingPoint, String endingPoint, Short passengersLimit, LocalDateTime startingTime, LocalDateTime endingTime) {
        this.driver = driver;
        this.car = car;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.passengersLimit = passengersLimit;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }
}
