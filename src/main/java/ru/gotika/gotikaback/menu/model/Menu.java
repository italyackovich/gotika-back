package ru.gotika.gotikaback.menu.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.restaurant.model.Restaurant;

import java.util.List;

@Entity
@Data
public class Menu {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "menu")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Dish> dishList;

}
