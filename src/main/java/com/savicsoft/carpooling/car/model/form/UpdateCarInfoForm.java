package com.savicsoft.carpooling.car.model.form;

import com.savicsoft.carpooling.car.enumeration.FuelType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarInfoForm {
    private String mark;
    private String model;
    private String registrationNumber;
    private String color;
    private int year;
    private double fuelEfficiency;
    private FuelType fuelType;
}
