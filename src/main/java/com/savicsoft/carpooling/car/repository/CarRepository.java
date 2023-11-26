package com.savicsoft.carpooling.car.repository;

import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    Optional<List<Car>> findAllByUserId(UUID userId);
    void deleteCarsByUserId(UUID userId);
    void deleteCarById(UUID carId);
    Optional<Car> findCarById(UUID carId);
}
