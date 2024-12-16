package ru.gotika.gotikaback.payment.service;

import java.util.Map;

public interface YookassaService {
    Map<String, Object> createPayment(Double amount, String description, String confirmationUrl, Long orderId, String idempotenceKey);
    Map<String, Object> getPaymentStatus(String paymentId);
}
