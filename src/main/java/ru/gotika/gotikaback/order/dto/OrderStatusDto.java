package ru.gotika.gotikaback.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.order.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private OrderStatus status;
}
