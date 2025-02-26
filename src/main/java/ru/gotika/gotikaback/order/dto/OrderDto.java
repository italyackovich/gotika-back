package ru.gotika.gotikaback.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull(message = "Total amount cannot be a null")
    @PositiveOrZero(message = "Total amount must be at least 0")
    private Double totalAmount;

    private LocalTime deliveryTime;
    private List<OrderItemDto> orderItems;

    @NotNull(message = "User id cannot be a null")
    @PositiveOrZero(message = "User id must be at least 0")
    private Long userId;

    @NotNull(message = "Restaurant id cannot be a null")
    @PositiveOrZero(message = "Restaurant id must be at least 0")
    private Long restaurantId;
    private RequestPaymentDto payment;

}
