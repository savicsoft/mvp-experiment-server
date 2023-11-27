package com.savicsoft.carpooling.car.repository;

import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    List<Car> findAllByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);
    void deleteById(UUID carId);
    Optional<Car> findCarById(UUID carId);
}
