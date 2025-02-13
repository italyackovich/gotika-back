package ru.gotika.gotikaback.order.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {MapperUtil.class},
        imports = {LocalDateTime.class, Collectors.class})
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

}
