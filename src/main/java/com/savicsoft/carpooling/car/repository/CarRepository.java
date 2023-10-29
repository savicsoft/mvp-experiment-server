package com.savicsoft.carpooling.car.repository;

import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByUserId(Long id);
    List<Car> findAll();
    void deleteCarsByUserId(Long id);
    void deleteByCarId(Long car_id);
    void deleteByRegistrationNumber(String registration);
    Car findCarByRegistrationNumber(String registration);
    Car findCarByCarId(Long car_id);
    Car findCarByUuid(UUID uuid);
}
