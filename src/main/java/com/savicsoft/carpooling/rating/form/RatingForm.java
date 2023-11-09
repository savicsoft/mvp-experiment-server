package com.savicsoft.carpooling.rating.form;

import com.savicsoft.carpooling.rating.enumeration.RatingType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingForm {

    @NotEmpty(message = "UUID must be given")
    private UUID uuid;
    private RatingType ratingType = RatingType.DRIVER;

}
