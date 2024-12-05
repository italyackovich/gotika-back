package ru.gotika.gotikaback.menu.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.menu.enums.DishCategory;
import ru.gotika.gotikaback.order.model.OrderItem;

@Entity
@Data
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DishCategory category;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToOne(mappedBy = "dish")
    private OrderItem orderItem;


}
