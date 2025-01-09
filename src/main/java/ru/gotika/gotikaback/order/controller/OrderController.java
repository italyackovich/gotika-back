package ru.gotika.gotikaback.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.dto.StatusDto;
import ru.gotika.gotikaback.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<OrderDto> changeStatus(@PathVariable Long id, @RequestBody StatusDto statusDto) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, statusDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
