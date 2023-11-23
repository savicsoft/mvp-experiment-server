package com.savicsoft.carpooling.review.model.dto;

import com.savicsoft.carpooling.review.model.enumeration.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Review Model information"
)
public class ReviewDTO {
    private UUID id;
    @Schema(description = "User that gets the review")
    private UUID ratedUserId;
    private double rating;
    private String comment;
    private LocalDateTime date;
    private boolean anonymous;
    @Schema(description = "User that leaves the review")
    private UUID raterUserId;
    @Schema(description = "Who left the review")
    private ReviewType reviewType;


}
