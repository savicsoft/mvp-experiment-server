package com.savicsoft.carpooling.review.model.mapper;

import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewDTOMapper {

    ReviewDTOMapper INSTANCE = Mappers.getMapper(ReviewDTOMapper.class);
    @Mapping(source = "ratedUser.id", target = "ratedUserId")
    @Mapping(source = "raterUser.id", target = "raterUserId")
    ReviewDTO mapToReviewDTO(Review review);
    @Mapping(source = "ratedUser.id", target = "ratedUserId")
    @Mapping(source = "raterUser.id", target = "raterUserId")
    List<ReviewDTO> mapToReviewDTOList(List<Review> review);

}
