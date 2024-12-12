package ru.gotika.gotikaback.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.order.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
