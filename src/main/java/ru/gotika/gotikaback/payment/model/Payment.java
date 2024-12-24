package ru.gotika.gotikaback.payment.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.payment.enums.PaymentMethod;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

    private String yookassaPaymentId;

    private String confirmationUrl;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
