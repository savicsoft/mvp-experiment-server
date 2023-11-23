package com.savicsoft.carpooling.user.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPrefForm {
    private String language;
    private String music;
    private String smoking;
    private String communication;
}
