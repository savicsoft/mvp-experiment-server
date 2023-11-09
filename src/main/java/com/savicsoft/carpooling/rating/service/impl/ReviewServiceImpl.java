package com.savicsoft.carpooling.rating.service.impl;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.models.entity.Review;
import com.savicsoft.carpooling.rating.models.mapper.ReviewMapper;
import com.savicsoft.carpooling.rating.repository.ReviewRepository;
import com.savicsoft.carpooling.rating.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public ReviewDTO createReview(Review review) {
         reviewRepository.save(review);
        return reviewMapper.reviewToReviewDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public List<ReviewDTO> getAllReviewsByUserId(Long id) {
        return null;
    }

    @Override
    public ReviewDTO getReviewByUserId(Long id) {
        return null;
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        return null;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return null;
    }

    @Override
    public ReviewDTO getReviewByUuid(UUID uuid) {
        return null;
    }
}
