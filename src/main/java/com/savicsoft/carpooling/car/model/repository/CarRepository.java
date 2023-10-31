package com.savicsoft.carpooling.car.model.repository;

import com.savicsoft.carpooling.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
