package com.savicsoft.carpooling.car.model.form;

import com.savicsoft.carpooling.car.model.enumeration.FuelType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CreateCarForm {
    private String mark;
    private String model;
    @NotEmpty(message = "Registration number cannot be empty")
    private String registrationNumber;
    private String color;
    private int year;
    @NotNull(message = "Fuel efficiency cannot be empty")
    private double fuelEfficiency;
    @NotEmpty(message = "Fuel type cannot be empty")
    private FuelType fuelType;
    @ArraySchema(minItems = 0, maxItems = 5, schema = @Schema(type = "string", format = "binary"))
    private List<MultipartFile> pictures;
}