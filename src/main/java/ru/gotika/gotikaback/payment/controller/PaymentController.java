package ru.gotika.gotikaback.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.dto.ResponsePaymentDto;
import ru.gotika.gotikaback.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePaymentDto> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponsePaymentDto> createPayment(@RequestBody RequestPaymentDto payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<ResponsePaymentDto> confirmPayment(@RequestParam String paymentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId));
    }

    @PostMapping("/webhook-change-status")
    public ResponseEntity<String> handleYookassaWebhook(@RequestBody PaymentNotificationDto notification) {
        paymentService.webhookYooKassa(notification);
        return ResponseEntity.ok("Webhook received");
    }

}
