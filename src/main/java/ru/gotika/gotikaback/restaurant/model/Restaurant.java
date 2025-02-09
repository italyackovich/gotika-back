package ru.gotika.gotikaback.restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.model.User;

import java.util.List;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String openingHours;

    private Float rating;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Menu menu;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orderList;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviewList;


}
