package ru.gotika.gotikaback.restaurant.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.menu.mapper.MenuMapper;
import ru.gotika.gotikaback.order.mapper.OrderMapper;
import ru.gotika.gotikaback.restaurant.dto.RequestRestaurantDto;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, OrderMapper.class, MenuMapper.class})
public interface RestaurantMapper {
    ResponseRestaurantDto restaurantToResponseRestaurantDto(Restaurant restaurant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Restaurant requestRestaurantDtoToRestaurant(RequestRestaurantDto restaurantDto);
}
