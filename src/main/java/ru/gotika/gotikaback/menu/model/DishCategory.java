package ru.gotika.gotikaback.menu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DishCategory {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
