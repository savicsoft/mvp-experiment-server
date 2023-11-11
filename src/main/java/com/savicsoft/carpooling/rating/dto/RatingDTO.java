package com.savicsoft.carpooling.rating.dto;

import com.savicsoft.carpooling.rating.enumeration.RatingType;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private UUID uuid;
    private User user;
    private double rating;
    private String comment;
    private LocalDateTime date;
    private boolean anonymous;
    private User ratingUser;
    private RatingType ratingType;


}
