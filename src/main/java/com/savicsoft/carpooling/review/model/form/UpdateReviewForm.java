package com.savicsoft.carpooling.review.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Update Review Form information"
)
public class UpdateReviewForm {
    @NotNull(message = "Rating must be added")
    private double rating;
    private String comment;
    @NotNull(message = "Status must be added")
    private boolean anonymous;

}
