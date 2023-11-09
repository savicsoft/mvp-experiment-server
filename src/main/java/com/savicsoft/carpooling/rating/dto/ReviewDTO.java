package com.savicsoft.carpooling.rating.dto;

import com.savicsoft.carpooling.rating.models.enumerations.RatingGrade;
import com.savicsoft.carpooling.user.model.entity.User;

import java.util.UUID;

public class ReviewDTO {
    private int id;
    private UUID uuid;
    private RatingGrade rating;
    private String description;
    private User user;

}
