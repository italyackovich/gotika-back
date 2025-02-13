package ru.gotika.gotikaback.order.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.menu.model.Dish;


@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

}
