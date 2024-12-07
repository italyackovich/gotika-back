package ru.gotika.gotikaback.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.user.mapper.UserMapper;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {UserMapper.class})
public interface RestaurantMapper {
    @Mapping(target = "userList", source = "userList")
    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    @Mapping(target = "userList", ignore = true)
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    @Mapping(target = "userList", source = "userList")
    List<RestaurantDto> restaurantListToRestaurantDtoList(List<Restaurant> restaurantList);
}
