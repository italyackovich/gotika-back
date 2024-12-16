package ru.gotika.gotikaback.order.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.menu.mapper.DishMapper;
import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.model.OrderItem;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> orderItemList);
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    @Mapping(target = "dish", source = "dishId", qualifiedByName = "idToDish")
    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);

    @Named("idToOrder")
    default Order idToOrder(Long orderId) {
        if (orderId == null) return null;
        Order order = new Order();
        order.setId(orderId);
        return order;
    }

    @Named("idToDish")
    default Dish idToDish(Long dishId) {
        if (dishId == null) return null;
        Dish dish = new Dish();
        dish.setId(dishId);
        return dish;
    }
}
