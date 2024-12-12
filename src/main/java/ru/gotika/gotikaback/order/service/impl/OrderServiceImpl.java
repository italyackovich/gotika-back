package ru.gotika.gotikaback.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.dto.StatusDto;
import ru.gotika.gotikaback.order.mapper.OrderMapper;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.repository.OrderRepository;
import ru.gotika.gotikaback.order.service.OrderService;

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
        return orderMapper.orderToOrderDto(orderRepository.findById(id).orElse(null));
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderRepository.save(orderMapper.orderDtoToOrder(orderDto));
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.save(orderMapper.orderDtoToOrder(orderDto));
            return orderDto;
        }).orElse(null);
    }

    @Override
    public OrderDto changeOrderStatus(Long id, StatusDto statusDto) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(statusDto.getStatus());
            orderRepository.save(order);
            return orderMapper.orderToOrderDto(order);
        }).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
