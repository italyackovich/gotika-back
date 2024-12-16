package ru.gotika.gotikaback.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByYookassaPaymentId(String yookassaPaymentId);
}
