package ru.gotika.gotikaback.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.payment.enums.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentDto {

    @NotNull(message = "Order id cannot be a null")
    @PositiveOrZero(message = "Order id cannot be at least 0")
    private Long orderId;
    private PaymentMethod paymentMethod;
}
