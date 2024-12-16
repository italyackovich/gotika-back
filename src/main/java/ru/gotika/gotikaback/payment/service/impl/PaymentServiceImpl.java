package ru.gotika.gotikaback.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.repository.OrderRepository;
import ru.gotika.gotikaback.payment.dto.PaymentDto;
import ru.gotika.gotikaback.payment.enums.PaymentMethod;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;
import ru.gotika.gotikaback.payment.mapper.PaymentMapper;
import ru.gotika.gotikaback.payment.model.Payment;
import ru.gotika.gotikaback.payment.repository.PaymentRepository;
import ru.gotika.gotikaback.payment.service.PaymentService;
import ru.gotika.gotikaback.payment.service.YookassaService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final YookassaService yookassaService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String idempotenceKey = UUID.randomUUID().toString();

        // Создать платеж
        Map<String, Object> paymentResponse = yookassaService.createPayment(
                100.00,
                "Оплата заказа №" + paymentDto.getOrderId(),
                "http://localhost:8080/payments/confirmation",
                paymentDto.getOrderId(),
                idempotenceKey
        );

        // Сохранить данные в БД
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        payment.setOrder(order);
        payment.setPaymentMethod(PaymentMethod.NON_CASH);
        payment.setYookassaPaymentId((String) paymentResponse.get("id"));
        payment.setConfirmationUrl((String) ((Map) paymentResponse.get("confirmation")).get("confirmation_url"));
        paymentRepository.save(payment);
        System.out.println((String) ((Map) paymentResponse.get("confirmation")).get("confirmation_url"));
        return paymentMapper.paymentToPaymentDto(payment);
    }

    @Override
    public PaymentDto confirmPayment(String yookassaPaymentId) {

        Map<String, Object> paymentStatus = yookassaService.getPaymentStatus(yookassaPaymentId);
        Payment payment = paymentRepository.findByYookassaPaymentId(yookassaPaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("succeeded".equals(paymentStatus.get("status"))) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
        }

        return paymentMapper.paymentToPaymentDto(payment);
    }
}
