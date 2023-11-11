package com.savicsoft.carpooling.rating.models.mapper;

import com.savicsoft.carpooling.rating.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.models.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDTO reviewToReviewDTO(Review review);
    Review reviewDTOToReview(ReviewDTO reviewDTO);
    List<ReviewDTO> reviewListToReviewDTOList(List<Review> reviewList);
    List<Review> reviewDTOListToReviewList(List<ReviewDTO> reviewDTOList);
}
