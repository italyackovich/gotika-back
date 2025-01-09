package ru.gotika.gotikaback.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.payment.dto.PaymentDto;
import ru.gotika.gotikaback.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<PaymentDto> confirmPayment(@RequestParam String paymentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId));
    }

}
