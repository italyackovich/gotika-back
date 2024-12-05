package ru.gotika.gotikaback.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.models.User;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String openingHours;
    private Menu menu;
    private List<User> userList;
    private List<Order> orderList;
    private List<Review> reviewList;
}
