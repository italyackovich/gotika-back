package ru.gotika.gotikaback.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.models.User;

import java.util.List;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String picture;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String openingHours;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orderList;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviewList;


}
