package ru.gotika.gotikaback.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.order.dto.OrderStatusDto;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.service.OrderService;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
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

    @GetMapping("/reports")
    public ResponseEntity<?> generateOrderReport(@RequestParam Long restaurantId) throws IOException {
        List<Order> orders = orderService.getOrdersForLastMonth(restaurantId);
        ByteArrayOutputStream outputStream = getByteArrayOutputStream(orders);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders_report.txt");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .contentLength(outputStream.size())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @PatchMapping("/{id}/statuses")
    public ResponseEntity<OrderDto> changeStatus(@PathVariable Long id, @RequestBody OrderStatusDto statusDto) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, statusDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(List<Order> orders) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write("Отчет заказов за последний месяц\n\n");
            for (Order order : orders) {
                writer.write("ID заказа: " + order.getId() + "\n");
                writer.write("Имя клиента: " + order.getUser().getFirstName() + " " + order.getUser().getLastName() + "\n");
                writer.write("Сумма заказа: " + order.getTotalAmount() + "\n");
                writer.write("Дата заказа: " + order.getOrderDate() + "\n\n");
            }
        }
        return outputStream;
    }
}
