package ru.gotika.gotikaback.payment.service;

import ru.gotika.gotikaback.payment.dto.PaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto confirmPayment(String yookassaPaymentId);
    void webhookYooKassa(PaymentNotificationDto notification);
    void changePaymentStatus(Long paymentId, PaymentStatus status);
    PaymentDto getPayment(Long id);
}
