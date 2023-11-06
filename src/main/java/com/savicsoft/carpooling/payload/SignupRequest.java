package com.savicsoft.carpooling.payload;

import jakarta.validation.constraints.*;

import java.util.Date;

public class SignupRequest {

    private String firstName;

    private String lastName;
    private Date birthDate;
    private String country;
    private String city;
    private boolean isDriver;


    private String email;


    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public Date getBirthDate(){
        return  birthDate;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public boolean getDriver(){
        return isDriver;
    }

    public void setDriver(Boolean isDriver){
        this.isDriver = isDriver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}