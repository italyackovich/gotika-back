package ru.gotika.gotikaback.order.service;

import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.dto.OrderStatusDto;
import ru.gotika.gotikaback.order.enums.OrderStatus;
import ru.gotika.gotikaback.order.model.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long id);
    OrderDto createOrder(OrderDto orderDto);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    OrderDto changeOrderStatus(Long id, OrderStatusDto statusDto);
    List<Order> getOrdersForLastMonth(Long restaurantId);
    void deleteOrder(Long id);
    void changeOrderStatusByPaymentId(Long paymentId, OrderStatus status);
}
