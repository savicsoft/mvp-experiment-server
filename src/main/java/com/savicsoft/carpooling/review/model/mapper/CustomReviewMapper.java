package com.savicsoft.carpooling.review.model.mapper;

import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.entity.Review;
import com.savicsoft.carpooling.review.model.form.CreateReviewForm;
import com.savicsoft.carpooling.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomReviewMapper {
    private final UserRepository userRepository;

    public Review mapToReview(ReviewDTO reviewDTO){
        return Review.builder()
                .id(reviewDTO.getId())
                .ratedUser(userRepository.findById(reviewDTO.getRatedUserId()).orElse(null))
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .date(reviewDTO.getDate())
                .anonymous(reviewDTO.isAnonymous())
                .raterUser(userRepository.findById(reviewDTO.getRaterUserId()).orElse(null))
                .reviewType(reviewDTO.getReviewType())
                .build();
    }

    public Review mapToReview(CreateReviewForm reviewForm){
        return Review.builder()
                .id(UUID.randomUUID())
                .ratedUser(userRepository.findById(reviewForm.getRatedUserId()).orElse(null))
                .rating(reviewForm.getRating())
                .comment(reviewForm.getComment())
                .date(reviewForm.getDate())
                .anonymous(reviewForm.isAnonymous())
                .reviewType(reviewForm.getReviewType())
                .build();
    }
}
