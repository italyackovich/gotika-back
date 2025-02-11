package ru.gotika.gotikaback.order.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.model.OrderItem;

import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {MapperUtil.class},
        imports = {LocalTime.class})
public interface OrderItemMapper {

    List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> orderItemList);
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    @Mapping(target = "dish", source = "dishId", qualifiedByName = "idToDish")
    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);
}
