package com.savicsoft.carpooling.review.controller;

import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.form.CreateReviewForm;
import com.savicsoft.carpooling.review.model.form.UpdateReviewForm;
import com.savicsoft.carpooling.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(
        name = "Review resource",
        description = "Operations to interact with reviews"
)
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "Retrieve all reviews by User's id",
            description = "Retrieve all reviews that belong to User by his id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/rated/{id}")
    public ResponseEntity<List<ReviewDTO>> getAllByRatedUserId(@PathVariable("id") UUID id) {
        List<ReviewDTO> reviews = reviewService.findByRatedUserId(id);
        return ResponseEntity.ok(reviews);
    }

    @Operation(
            summary = "Retrieve all reviews by User's id",
            description = "Retrieve all reviews that User left by his id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/rater/{id}")
    public ResponseEntity<List<ReviewDTO>> getAllRatingsLeftByUser(@PathVariable("id") UUID id){
        List<ReviewDTO> reviews = reviewService.findByRaterUserId(id);
        return ResponseEntity.ok(reviews);
   }

    @Operation(
            summary = "Retrieve a Review by id",
            description = "Retrieve a Review by its id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getRatingById(@PathVariable("id") UUID id){
        ReviewDTO review = reviewService.findById(id);
        return ResponseEntity.ok(review);
    }

    @Operation(
            summary = "Create a Review",
            description = "Create a Review"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status: 201 -> Created"
    )
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody @Valid CreateReviewForm reviewToSave){
        ReviewDTO savedReview = reviewService.save(reviewToSave);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a Review",
            description = "Update a Review"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable UUID id,
                                                  @RequestBody @Valid UpdateReviewForm reviewToUpdate){
        ReviewDTO updatedReview = reviewService.update(id, reviewToUpdate);
        return ResponseEntity.ok(updatedReview);
    }

    @Operation(
            summary = "Delete a Review",
            description = "Delete a Review"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> createReview(@PathVariable UUID id){
        Boolean isDeleted = reviewService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}


