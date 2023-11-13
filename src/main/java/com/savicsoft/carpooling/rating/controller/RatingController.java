package com.savicsoft.carpooling.rating.controller;

import com.savicsoft.carpooling.rating.model.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST Apis for Rating resource",
        description = "Operations to interact with reviews"
)
public class RatingController {

    private final ReviewService reviewService;

    @Operation(
            summary = "Retrieve all reviews by User's UUID",
            description = "Retrieve all reviews that belong to User by his UUID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/rated/{UUID}")
    public ResponseEntity<List<ReviewDTO>> getAllByRatedUserUUID (@PathVariable("UUID") UUID uuid) {
        List<ReviewDTO> reviews = reviewService.findByRatedUserUuid(uuid);
        return ResponseEntity.ok(reviews);
    }

    @Operation(
            summary = "Retrieve all reviews by User's UUID",
            description = "Retrieve all reviews that User left by his UUID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    //TODO: Method should be implemented using the Authentication. this is an example how to implement.
    @GetMapping("/rater/{UUID}")
    //public ResponseEntity<HttpResponse> getAllRatingsLeftByUser(Authentication auth){
    public ResponseEntity<List<ReviewDTO>> getAllRatingsLeftByUser(@PathVariable("UUID") UUID uuid){
        //UserDTO user = (UserDTO) auth.getPrincipal();
        //List<ReviewDTO> ratings = reviewService.findByRatingUser_Uuid(user.getUUID);
        List<ReviewDTO> reviews = reviewService.findByRaterUserUuid(uuid);
        return ResponseEntity.ok(reviews);
   }

    @Operation(
            summary = "Retrieve a Review by UUID",
            description = "Retrieve a Review by its UUID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status: 200 -> Success"
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<ReviewDTO> getRatingByUUID(@PathVariable("UUID") UUID UUID){
        ReviewDTO review = reviewService.findByUuid(UUID);
        return ResponseEntity.ok(review);
    }

    @Operation(
            summary = "Create a Review",
            description = "Create a Review"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status: 200 -> Created"
    )
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody @Valid ReviewDTO reviewDTO){
        ReviewDTO savedReview = reviewService.save(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

}


