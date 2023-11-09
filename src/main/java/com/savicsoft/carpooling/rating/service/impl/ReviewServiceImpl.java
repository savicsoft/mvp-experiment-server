package com.savicsoft.carpooling.rating.service.impl;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.models.entity.Review;
import com.savicsoft.carpooling.rating.models.mapper.ReviewMapper;
import com.savicsoft.carpooling.rating.repository.ReviewRepository;
import com.savicsoft.carpooling.rating.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.reviewDTOToReview(reviewDTO);
        reviewRepository.save(review);
        return reviewMapper.reviewToReviewDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review updatedReview = reviewMapper.reviewDTOToReview(reviewDTO);
        Optional<Review> review = reviewRepository.findById(id);

       if(review.isPresent()){
           review.get().setDescription(updatedReview.getDescription());
           review.get().setRating(updatedReview.getRating());
           reviewRepository.save(review.get());
           return reviewMapper.reviewToReviewDTO(review.get());
       }
       else{
       return null;
       }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<Review> review = reviewRepository.findById(id);

        if(review.isPresent()){
            reviewRepository.deleteById(id);
        }

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
        Optional<Review> review = reviewRepository.findById(id);

        return review.map(reviewMapper::reviewToReviewDTO).orElse(null);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
       List<Review> reviews = reviewRepository.findAll();
       return reviewMapper.reviewListToReviewDTOList(reviews);
    }

    @Override
    public ReviewDTO getReviewByUuid(UUID uuid) {
        return null;
    }
}
