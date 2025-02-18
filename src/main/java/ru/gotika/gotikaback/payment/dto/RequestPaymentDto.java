package ru.gotika.gotikaback.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.payment.enums.PaymentMethod;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentDto {

    private Long id;

    @NotNull(message = "Order id cannot be a null")
    @PositiveOrZero(message = "Order id cannot be at least 0")
    private Long orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private String yookassaPaymentId;
    private String confirmationUrl;
}
