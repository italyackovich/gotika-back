package ru.gotika.gotikaback.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.user.dto.UserDto;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRestaurantDto {

    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private String openingHours;

    private String imageUrl;

    private Float rating;

    private MenuDto menu;

    private List<UserDto> userList;

    private List<OrderDto> orderList;

    private List<ReviewDto> reviewList;
}
