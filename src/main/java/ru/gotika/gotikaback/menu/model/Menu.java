package ru.gotika.gotikaback.menu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonBackReference
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Dish> dishList;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Category> categoryList;

}
