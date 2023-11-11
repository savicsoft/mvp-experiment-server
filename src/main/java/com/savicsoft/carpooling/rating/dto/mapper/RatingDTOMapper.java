package com.savicsoft.carpooling.rating.dto.mapper;

import com.savicsoft.carpooling.rating.dto.RatingDTO;
import com.savicsoft.carpooling.rating.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RatingDTOMapper {

    RatingDTOMapper INSTANCE = Mappers.getMapper(RatingDTOMapper.class);
    RatingDTO mapToRatingDTO(Rating rating);
    @Mapping(target = "id", ignore = true)
    Rating mapToRating(RatingDTO ratingDTO);
    List<RatingDTO> mapToRatingDTOList(List<Rating> rating);

}
