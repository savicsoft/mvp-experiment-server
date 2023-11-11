package com.savicsoft.carpooling.rating.service;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO review);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
    List<ReviewDTO> getAllReviewsByUserId(Long id);
    ReviewDTO getReviewById(Long id);
    List<ReviewDTO> getAllReviews();


}
