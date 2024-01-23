package com.savicsoft.carpooling.review.model.form;

import com.savicsoft.carpooling.review.model.enumeration.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Create Review Form information"
)
public class CreateReviewForm {
    @Schema(description = "User that gets the review")
    @NotNull(message = "User field must be filled appropriately")
    private UUID ratedUserId;
    @NotNull(message = "Rating must be added")
    private double rating;
    private String comment;
    private LocalDateTime date;
    @NotNull(message = "Status must be added")
    private boolean anonymous;
    @Schema(description = "Who left the review")
    private ReviewType reviewType;


}
