package com.savicsoft.carpooling.payload;


import jakarta.validation.constraints.*;

import java.util.Date;

public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    private String tel;

    @NotBlank
    private Date birthDate;

    @NotBlank
    private String country;

    @NotBlank
    private String city;






    public String getEmail() {
        return email;
    }

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public Date getBirthDate(){return birthDate;}

    public String getCountry(){return country;}

    public String getCity(){return city;}

    public String getTel(){return tel;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName){this.firstName = firstName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public void setCountry(String country){this.country=country;}

    public void setCity(String city){this.city=city
    ;}

    public void setBirthDate(Date birthDate){this.birthDate=birthDate;}

    public void setTel(String tel){this.tel = tel;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}