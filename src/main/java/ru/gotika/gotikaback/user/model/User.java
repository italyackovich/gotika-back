package ru.gotika.gotikaback.user.model;

import jakarta.persistence.*;
import lombok.*;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;
import ru.gotika.gotikaback.notification.model.Notification;
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
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ValidPhoneNumber
    private String phoneNumber;

    private String imageUrl;

    private String address;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user")
    private List<Notification> notificationList;
}
