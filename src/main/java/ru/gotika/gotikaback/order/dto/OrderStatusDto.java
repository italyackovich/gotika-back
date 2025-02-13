package ru.gotika.gotikaback.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.order.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {

    @NotNull(message = "Order status cannot be a null")
    private OrderStatus status;
}
