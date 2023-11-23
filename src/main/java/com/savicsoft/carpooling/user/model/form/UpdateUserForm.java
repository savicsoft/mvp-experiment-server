package com.savicsoft.carpooling.user.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserForm {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private String city;
    private boolean driver;
}
