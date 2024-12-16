package ru.gotika.gotikaback.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.mapper.OrderItemMapper;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.model.OrderItem;
import ru.gotika.gotikaback.order.repository.OrderItemRepository;
import ru.gotika.gotikaback.order.service.OrderItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemMapper.orderItemListToOrderItemDtoList(orderItemRepository.findAll());
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        return orderItemMapper.orderItemToOrderItemDto(orderItemRepository.
                findById(id).
                orElse(null)
        );
    }

    @Override
    public OrderItemDto saveOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDto);
        orderItem.setPrice(0.0);
        if (orderItem.getDish().getPrice() != null) {
            orderItem.setPrice(orderItem.getQuantity() * orderItem.getDish().getPrice());
        }
        orderItemRepository.save(orderItem);
        return orderItemMapper.orderItemToOrderItemDto(orderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        return orderItemRepository.findById(id).map(orderItem -> {
            OrderItem newOrderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDto);
            newOrderItem.setPrice(newOrderItem.getQuantity() * newOrderItem.getDish().getPrice());
            orderItemRepository.save(newOrderItem);
            return orderItemMapper.orderItemToOrderItemDto(newOrderItem);
        }).orElse(null);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
