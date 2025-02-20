package ru.gotika.gotikaback.payment.service;

import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.dto.ResponsePaymentDto;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;

public interface PaymentService {
    ResponsePaymentDto createPayment(RequestPaymentDto paymentDto);
    ResponsePaymentDto confirmPayment(String yookassaPaymentId);
    void webhookYooKassa(PaymentNotificationDto notification);
    void changePaymentStatus(Long paymentId, PaymentStatus status);
    ResponsePaymentDto getPayment(Long id);
}
