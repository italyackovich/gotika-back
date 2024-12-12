package ru.gotika.gotikaback.menu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String description;

    @Column(nullable = false)
    private Double price;

    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DishCategory category;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToOne(mappedBy = "dish")
    @JsonBackReference
    private OrderItem orderItem;


}
