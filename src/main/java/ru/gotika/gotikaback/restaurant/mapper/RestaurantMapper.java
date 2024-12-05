package ru.gotika.gotikaback.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.model.Restaurant;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {
    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);
    List<RestaurantDto> restaurantListToRestaurantDtoList(List<Restaurant> restaurantList);
}
