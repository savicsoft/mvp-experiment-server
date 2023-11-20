package com.savicsoft.carpooling.ride.model.entity;

import com.savicsoft.carpooling.ride.model.enumeration.RideStatus;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passenger_ride")
public class PassengerRide {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private User passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_ride_id")
    private DriverRide driverRide;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name="ending_point")
    private String endingPoint;

    @Column(name = "pick_up_time")
    private LocalDateTime pickUpTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "ride_status")
    private RideStatus rideStatus;

    @Column(name = "ride_price", precision = 6, scale = 2)
    private BigDecimal ridePrice;

    public PassengerRide(User passenger, DriverRide driverRide, String startingPoint, String endingPoint, LocalDateTime pickUpTime, RideStatus rideStatus, BigDecimal ridePrice) {
        this.passenger = passenger;
        this.driverRide = driverRide;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.pickUpTime = pickUpTime;
        this.rideStatus = rideStatus;
        this.ridePrice = ridePrice;
    }
}
