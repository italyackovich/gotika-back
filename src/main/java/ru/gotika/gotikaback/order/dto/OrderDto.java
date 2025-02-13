package ru.gotika.gotikaback.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.payment.dto.PaymentDto;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull(message = "Total amount cannot be a null")
    private Double totalAmount;

    private LocalTime deliveryTime;
    private List<OrderItemDto> orderItems;

    @NotNull(message = "User id cannot be a null")
    private Long userId;

    @NotNull(message = "Restaurant id cannot be a null")
    private Long restaurantId;
    private PaymentDto payment;

}
