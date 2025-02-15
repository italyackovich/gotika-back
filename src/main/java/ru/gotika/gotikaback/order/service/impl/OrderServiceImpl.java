package ru.gotika.gotikaback.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.dto.OrderStatusDto;
import ru.gotika.gotikaback.order.enums.OrderStatus;
import ru.gotika.gotikaback.order.exception.OrderNotFoundException;
import ru.gotika.gotikaback.order.mapper.OrderMapper;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.model.OrderItem;
import ru.gotika.gotikaback.order.repository.OrderRepository;
import ru.gotika.gotikaback.order.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.orderListToOrderDtoList(orderRepository.findAll());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderMapper.orderToOrderDto(orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found")));
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.orderDtoToOrder(orderDto);
        order.setTotalAmount(0.0);
        order.setStatus(OrderStatus.NOT_PAID);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id).map(order -> {
            Order newOrder = orderMapper.orderDtoToOrder(orderDto);
            newOrder.setId(order.getId());
            Double totalAmount = order.getOrderItems().stream()
                    .mapToDouble(OrderItem::getPrice)
                    .sum();
            newOrder.setTotalAmount(totalAmount);
            orderRepository.save(newOrder);
            return orderMapper.orderToOrderDto(newOrder);
        }).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public OrderDto changeOrderStatus(Long id, OrderStatusDto statusDto) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(statusDto.getStatus());
            orderRepository.save(order);
            return orderMapper.orderToOrderDto(order);
        }).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public List<Order> getOrdersForLastMonth(Long restaurantId) {

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        LocalDateTime now = LocalDateTime.now();

        return orderRepository.findByRestaurantIdAndOrderDateBetween(restaurantId, oneMonthAgo, now);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void changeOrderStatusByPaymentId(Long paymentId, OrderStatus status) {
        orderRepository.findByPaymentId(paymentId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
