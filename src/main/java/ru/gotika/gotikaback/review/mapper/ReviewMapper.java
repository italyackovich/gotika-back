package ru.gotika.gotikaback.review.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    List<ReviewDto> reviewListToReviewDtoList(List<Review> reviewList);

    ReviewDto reviewToReviewDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    Review reviewDtoToReview(ReviewDto dto);

    @Named("idToRestaurant")
    default Restaurant idToRestaurant(Long restaurantId) {
        if (restaurantId == null) return null;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }

    @Named("idToUser")
    default User idToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}
