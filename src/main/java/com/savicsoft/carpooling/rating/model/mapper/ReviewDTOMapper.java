package com.savicsoft.carpooling.rating.model.mapper;

import com.savicsoft.carpooling.rating.model.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.model.entity.Review;
import com.savicsoft.carpooling.rating.service.ReviewService;
import com.savicsoft.carpooling.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ReviewDTOMapper {

    ReviewDTOMapper INSTANCE = Mappers.getMapper(ReviewDTOMapper.class);
    @Mapping(source = "ratedUser.uuid", target = "ratedUserId")
    @Mapping(source = "raterUser.uuid", target = "raterUserId")
    ReviewDTO mapToReviewDTO(Review review);
    @Mapping(source = "ratedUser.uuid", target = "ratedUserId")
    @Mapping(source = "raterUser.uuid", target = "raterUserId")
    List<ReviewDTO> mapToReviewDTOList(List<Review> review);

}
