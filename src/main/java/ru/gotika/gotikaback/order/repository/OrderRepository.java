package ru.gotika.gotikaback.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.order.model.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByPaymentId(Long paymentId);
}
