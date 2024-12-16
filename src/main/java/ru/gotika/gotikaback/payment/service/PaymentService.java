package ru.gotika.gotikaback.payment.service;

import ru.gotika.gotikaback.payment.dto.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto confirmPayment(String yookassaPaymentId);
}
