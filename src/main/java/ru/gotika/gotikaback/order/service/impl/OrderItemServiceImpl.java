package ru.gotika.gotikaback.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.menu.repository.DishRepository;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.mapper.OrderItemMapper;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.model.OrderItem;
import ru.gotika.gotikaback.order.repository.OrderItemRepository;
import ru.gotika.gotikaback.order.repository.OrderRepository;
import ru.gotika.gotikaback.order.service.OrderItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

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
        Order order = orderRepository.findById(orderItemDto.getOrderId()).orElse(null);
        Dish dish = dishRepository.findById(orderItemDto.getDishId()).orElse(null);
        orderItem.setPrice(0.0);
        assert dish != null;
        assert dish.getPrice() != null;
        orderItem.setPrice(orderItem.getQuantity() * dish.getPrice());
        assert order != null;
        order.setTotalAmount(order.getOrderItems().stream()
                .mapToDouble(OrderItem::getPrice).sum() + orderItem.getPrice());
        orderRepository.save(order);
        orderItemRepository.save(orderItem);
        return orderItemMapper.orderItemToOrderItemDto(orderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        return orderItemRepository.findById(id).map(orderItem -> {
            OrderItem newOrderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDto);
            newOrderItem.setId(orderItem.getId());
            Order order = orderRepository.findById(orderItemDto.getOrderId()).orElse(null);
            newOrderItem.setPrice(newOrderItem.getQuantity() * orderItem.getDish().getPrice());
            if (order != null) {
                order.setTotalAmount(order.getOrderItems()
                        .stream()
                        .mapToDouble(OrderItem::getPrice)
                        .sum() + newOrderItem.getPrice()
                );
                orderRepository.save(order);
            }
            orderItemRepository.save(newOrderItem);
            return orderItemMapper.orderItemToOrderItemDto(newOrderItem);
        }).orElse(null);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        OrderItem orderitem = orderItemRepository.findById(id).orElse(null);
        orderItemRepository.deleteById(id);

        if (orderitem != null) {
            Order order = orderRepository.findById(orderitem.getOrder().getId()).orElse(null);
            assert order != null;
            order.setTotalAmount(order.getOrderItems()
                    .stream()
                    .mapToDouble(OrderItem::getPrice).sum()
            );
            orderRepository.save(order);
        }
    }
}
