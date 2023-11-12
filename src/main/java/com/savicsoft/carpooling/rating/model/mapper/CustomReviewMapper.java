package com.savicsoft.carpooling.rating.model.mapper;

import com.savicsoft.carpooling.rating.model.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.model.entity.Review;
import com.savicsoft.carpooling.user.model.entity.User;
import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomReviewMapper {
    private final UserRepository userRepository;

    public Review mapToReview(ReviewDTO reviewDTO){
        return Review.builder()
                .uuid(reviewDTO.getUuid())
                .ratedUser(userRepository.findByUuid(reviewDTO.getRatedUserId()).orElse(null))
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .date(reviewDTO.getDate())
                .anonymous(reviewDTO.isAnonymous())
                .raterUser(userRepository.findByUuid(reviewDTO.getRaterUserId()).orElse(null))
                .reviewType(reviewDTO.getReviewType())
                .build();
    }
}
