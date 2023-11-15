package com.savicsoft.carpooling.car.dto;

import com.savicsoft.carpooling.car.enumeration.FuelType;
import com.savicsoft.carpooling.user.model.entity.User;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private UUID uuid;
    private UUID userUuid;
    private String registrationNumber;
    private String color;
    private int year;
    private FuelType fuelType;
    private double fuelEfficiency;
    private List<MultipartFile> pictures;
}
