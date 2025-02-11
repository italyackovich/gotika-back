package ru.gotika.gotikaback.restaurant.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.menu.mapper.MenuMapper;
import ru.gotika.gotikaback.order.mapper.OrderMapper;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.user.mapper.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, OrderMapper.class, MenuMapper.class})
public interface RestaurantMapper {
    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    List<RestaurantDto> restaurantListToRestaurantDtoList(List<Restaurant> restaurantList);
}
