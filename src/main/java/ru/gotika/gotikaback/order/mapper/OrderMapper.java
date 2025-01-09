package ru.gotika.gotikaback.order.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.enums.Status;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.user.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {Status.class, LocalDateTime.class, Collectors.class})
public interface OrderMapper {

    List<OrderDto> orderListToOrderDtoList(List<Order> orderList);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "userId", source = "user.id")
    OrderDto orderToOrderDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "orderDate", defaultExpression = "java(LocalDateTime.now())")
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    Order orderDtoToOrder(OrderDto orderDto);

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
