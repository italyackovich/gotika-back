package ru.gotika.gotikaback.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.gotika.gotikaback.common.annotation.ValidPassword;
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

    @NotBlank(message = "First name cannot be blank")
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false)
    private String email;

    @ValidPassword
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role cannot be blank")
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
