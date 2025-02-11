package ru.gotika.gotikaback.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidPassword;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;
import ru.gotika.gotikaback.order.dto.OrderDto;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.user.enums.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    private Long restaurantId;
    private String imageUrl;
    private Role role;

    @ValidPassword
    private String password;

    @ValidPhoneNumber
    private String phoneNumber;
    private String address;
    private List<OrderDto> orderList;
    private List<ReviewDto> reviewList;

}
