package ru.gotika.gotikaback.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.user.dto.UserDto;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Name cannot be null")
    private String address;

    @ValidPhoneNumber
    private String phoneNumber;

    @NotNull(message = "Name cannot be null")
    private String openingHours;

    private String imageUrl;

    private Float rating;

    private MenuDto menu;

    private List<UserDto> userList;

    private List<OrderDto> orderList;

    private List<ReviewDto> reviewList;
}
