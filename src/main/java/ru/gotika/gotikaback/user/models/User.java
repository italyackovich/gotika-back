package ru.gotika.gotikaback.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.enums.Role;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phoneNumber;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;
}
