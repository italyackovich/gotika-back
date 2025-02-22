package ru.gotika.gotikaback.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRestaurantDto {

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
}
