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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private String yookassaPaymentId;

    @Column(nullable = false)
    private String confirmationUrl;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
