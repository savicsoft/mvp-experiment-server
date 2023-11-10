package com.savicsoft.carpooling.rating.controller;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id){
        ReviewDTO reviewDto = reviewService.getReviewById(id);
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{userid}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsByUserId(@PathVariable Long userid){
        List<ReviewDTO> reviewDTO = reviewService.getAllReviewsByUserId(userid);
        return ResponseEntity.ok(reviewDTO);
    }
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO){
        ReviewDTO review = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO updatedReviewDTO){
        ReviewDTO reviewDTO = reviewService.updateReview(id, updatedReviewDTO);
        return ResponseEntity.ok(reviewDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted!");
    }


}
