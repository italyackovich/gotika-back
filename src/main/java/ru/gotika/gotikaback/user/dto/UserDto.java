package ru.gotika.gotikaback.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.user.enums.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long restaurantId;
    private Role role;
    private String phoneNumber;
    private String address;
    private List<Order> orderList;
    private List<Review> reviewList;

}
