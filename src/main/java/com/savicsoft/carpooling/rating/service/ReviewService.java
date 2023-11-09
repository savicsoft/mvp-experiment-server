package com.savicsoft.carpooling.rating.service;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.models.entity.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewDTO createReview(Review review);
    ReviewDTO updateReview(Long id);
    void deleteUser(Long id);
    List<ReviewDTO> getAllReviewsByUserId(Long id);
    ReviewDTO getReviewByUserId(Long id);
    ReviewDTO getReviewById(Long id);
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewByUuid(UUID uuid);

}
