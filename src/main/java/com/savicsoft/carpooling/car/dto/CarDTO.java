package com.savicsoft.carpooling.car.dto;

import com.savicsoft.carpooling.car.enumeration.FuelType;
import com.savicsoft.carpooling.car.model.entity.Car;
import com.savicsoft.carpooling.car.service.CarService;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.beans.BeanUtils;

import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor (error: constructor CarDTO() is already defined in class CarDTO @RequiredArgsConstructor)
public class CarDTO {

    private UUID uuid;
    private User user;
    private String registrationNumber;
    private String color;
    private int year;
    private FuelType fuelType;
    private String pictureUrl;


}
