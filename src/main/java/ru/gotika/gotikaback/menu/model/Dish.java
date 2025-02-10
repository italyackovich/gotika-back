package ru.gotika.gotikaback.menu.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "dish_category_id")
    private DishCategory dishCategory;


}
