package com.savicsoft.carpooling.rating.model.dto;

import com.savicsoft.carpooling.rating.model.enumeration.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Schema(hidden = true)
    private UUID uuid;
    @Schema(description = "User that gets the review")
    @NotNull(message = "User field must be filled appropriately")
    private UUID ratedUserId;
    @Schema(description = "Rating of the ride")
    @NotNull(message = "Rating must be added")
    private double rating;
    @Schema(description = "Drive comment")
    private String comment;
    @Schema(description = "Date of the drive")
    private LocalDateTime date;
    @Schema(description = "User wants to leave his review anonymously")
    private boolean anonymous;
    @Schema(description = "User that leaves the review")
    @NotNull(message = "Rater user field must be filled appropriately")
    private UUID raterUserId;
    @Schema(description = "Who left the review")
    private ReviewType reviewType;


}
