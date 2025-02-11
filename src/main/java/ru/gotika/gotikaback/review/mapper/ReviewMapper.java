package ru.gotika.gotikaback.review.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.review.model.Review;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface ReviewMapper {
    List<ReviewDto> reviewListToReviewDtoList(List<Review> reviewList);

    ReviewDto reviewToReviewDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    Review reviewDtoToReview(ReviewDto dto);
}
