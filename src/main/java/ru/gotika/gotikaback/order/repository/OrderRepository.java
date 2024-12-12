package ru.gotika.gotikaback.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gotika.gotikaback.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
