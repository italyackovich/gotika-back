package ru.gotika.gotikaback.order.service;

import ru.gotika.gotikaback.order.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems();
    OrderItemDto getOrderItemById(Long id);
    OrderItemDto saveOrderItem(OrderItemDto orderItemDto);
    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);
    void deleteOrderItemById(Long id);
}
