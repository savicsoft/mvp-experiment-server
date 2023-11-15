package com.savicsoft.carpooling.car.repository;

import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<List<Car>> findAllByUserUuid(UUID uuid);
    List<Car> findAll();
    void deleteCarsByUserId(Long id);
    void deleteById(Long car_id);
    void deleteByRegistrationNumber(String registration);
    Car findCarByRegistrationNumber(String registration);
    Car findCarById(Long car_id);
    Optional<Car> findCarByUuid(UUID uuid);
}
