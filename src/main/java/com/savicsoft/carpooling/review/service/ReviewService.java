package com.savicsoft.carpooling.review.service;

import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.form.CreateReviewForm;
import com.savicsoft.carpooling.review.model.form.UpdateReviewForm;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewDTO> findByRatedUserId(UUID ratedUserId);
    List<ReviewDTO> findByRaterUser();
    ReviewDTO findById(UUID id);
    ReviewDTO save(CreateReviewForm reviewToSave);

    ReviewDTO update(UUID id, UpdateReviewForm reviewToUpdate);

    boolean delete(UUID id);
}
