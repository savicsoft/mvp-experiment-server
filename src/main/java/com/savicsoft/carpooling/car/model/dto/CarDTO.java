package com.savicsoft.carpooling.car.model.dto;

import com.savicsoft.carpooling.car.model.enumeration.FuelType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID id;
    @NotNull
    private UUID userId;
    private String mark;
    private String model;
    @NotEmpty
    private String registrationNumber;
    private String color;
    private int year;
    @NotNull
    private FuelType fuelType;
    @NotNull
    private double fuelEfficiency;
    private List<MultipartFile> pictures;

    public int calculateNumOfPics() {
        return pictures != null ? pictures.size() : 0;
    }
}