package ru.gotika.gotikaback.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.mapper.OrderItemMapper;
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
        OrderItem orderItem = orderItemRepository.save(
                orderItemMapper.
                        orderItemDtoToOrderItem(orderItemDto)
        );
        return orderItemDto;
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        return orderItemRepository.findById(id).map(orderItem -> {
            orderItemRepository.save(orderItemMapper.orderItemDtoToOrderItem(orderItemDto));
            return orderItemDto;
        }).orElse(null);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
