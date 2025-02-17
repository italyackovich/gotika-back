package ru.gotika.gotikaback.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.order.enums.OrderStatus;
import ru.gotika.gotikaback.order.exception.OrderNotFoundException;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.order.repository.OrderRepository;
import ru.gotika.gotikaback.order.service.OrderService;
import ru.gotika.gotikaback.payment.dto.PaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.enums.PaymentMethod;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;
import ru.gotika.gotikaback.payment.exception.PaymentNotFoundException;
import ru.gotika.gotikaback.payment.mapper.PaymentMapper;
import ru.gotika.gotikaback.payment.model.Payment;
import ru.gotika.gotikaback.payment.repository.PaymentRepository;
import ru.gotika.gotikaback.payment.service.PaymentService;
import ru.gotika.gotikaback.payment.service.YookassaService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final YookassaService yookassaService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + paymentDto.getOrderId() + " not found"));

        String idempotenceKey = UUID.randomUUID().toString();

        // Создать платеж
        Map<String, Object> paymentResponse = yookassaService.createPayment(
                order.getTotalAmount(),
                "Оплата заказа №" + paymentDto.getOrderId(),
                "http://localhost:5173/",
                paymentDto.getOrderId(),
                idempotenceKey
        );

        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        payment.setPaymentMethod(PaymentMethod.NON_CASH);
        payment.setYookassaPaymentId((String) paymentResponse.get("id"));
        payment.setConfirmationUrl((String) ((Map) paymentResponse.get("confirmation")).get("confirmation_url"));
        paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDto(payment);
    }

    @Override
    public PaymentDto confirmPayment(String yookassaPaymentId) {

        Map<String, Object> paymentStatus = yookassaService.getPaymentStatus(yookassaPaymentId);
        Payment payment = paymentRepository.findByYookassaPaymentId(yookassaPaymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with yookassa's id: " + yookassaPaymentId + " not found"));

        if ("succeeded".equals(paymentStatus.get("status"))) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
        }

        return paymentMapper.paymentToPaymentDto(payment);
    }

    @Override
    public void webhookYooKassa(PaymentNotificationDto notification) {

        String yookassaPaymentId = notification.getObject().getId();
        Payment payment = paymentRepository
                .findByYookassaPaymentId(yookassaPaymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with yookassa's id: " + yookassaPaymentId + " not found"));

        Long paymentId = payment.getId();

        if ("payment.succeeded".equals(notification.getEvent())) {
            Double amount = notification.getObject().getAmount().getValue();

            orderService.changeOrderStatusByPaymentId(paymentId, OrderStatus.PAID);
            changePaymentStatus(paymentId, PaymentStatus.PAID);

            log.info("Платеж успешно завершен: ID = {}, Сумма = {}", paymentId, amount);

        } else if ("payment.canceled".equals(notification.getEvent())) {

            orderService.changeOrderStatusByPaymentId(paymentId, OrderStatus.CANCELED);
            changePaymentStatus(paymentId, PaymentStatus.CANCELED);

            log.info("Платеж отменен: ID = {}", paymentId);
        }
    }

    @Override
    public void changePaymentStatus(Long paymentId, PaymentStatus status) {
        paymentRepository.findById(paymentId).ifPresent(payment -> {
            payment.setPaymentStatus(status);
            paymentRepository.save(payment);
        });
    }

    @Override
    public PaymentDto getPayment(Long id) {
        return paymentMapper.paymentToPaymentDto(paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id: " + id + " not found")));
    }
}
