package ru.gotika.gotikaback.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;
import ru.gotika.gotikaback.payment.dto.PaymentNotificationDto;
import ru.gotika.gotikaback.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<RequestPaymentDto> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RequestPaymentDto> createPayment(@RequestBody RequestPaymentDto payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<RequestPaymentDto> confirmPayment(@RequestParam String paymentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId));
    }

    @PostMapping("/webhook-change-status")
    public ResponseEntity<String> handleYooKassaWebhook(@RequestBody PaymentNotificationDto notification) {
        paymentService.webhookYooKassa(notification);
        return ResponseEntity.ok("Webhook received");
    }

}
