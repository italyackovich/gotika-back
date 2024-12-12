package ru.gotika.gotikaback.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.order.enums.Status;
import ru.gotika.gotikaback.payment.model.Payment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Status status;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private LocalTime deliveryTime;
    private List<OrderItemDto> orderItems;
    private Long userId;
    private Long restaurantId;
    private Payment payment;

}
