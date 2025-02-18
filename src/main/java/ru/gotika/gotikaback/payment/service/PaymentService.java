package ru.gotika.gotikaback.payment.service;

import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;

public interface PaymentService {
    RequestPaymentDto createPayment(RequestPaymentDto paymentDto);
    RequestPaymentDto confirmPayment(String yookassaPaymentId);
    void webhookYooKassa(PaymentNotificationDto notification);
    void changePaymentStatus(Long paymentId, PaymentStatus status);
    RequestPaymentDto getPayment(Long id);
}
