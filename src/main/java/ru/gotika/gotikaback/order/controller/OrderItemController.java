package ru.gotika.gotikaback.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.order.dto.OrderItemDto;
import ru.gotika.gotikaback.order.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemDto>> findAll() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderItemDto> create(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.saveOrderItem(orderItemDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<OrderItemDto> update(@PathVariable Long id, @RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItemDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.noContent().build();
    }
}
