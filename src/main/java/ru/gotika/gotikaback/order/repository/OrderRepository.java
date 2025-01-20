package ru.gotika.gotikaback.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByPaymentId(Long paymentId);
    List<Order> findByRestaurantIdAndOrderDateBetween(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate);
}
