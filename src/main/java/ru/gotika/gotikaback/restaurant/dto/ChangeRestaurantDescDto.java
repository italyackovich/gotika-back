package ru.gotika.gotikaback.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.gotika.gotikaback.common.annotation.ValidPhoneNumber;

@Data
public class ChangeRestaurantDescDto {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Name cannot be null")
    private String address;

    @NotNull(message = "Name cannot be null")
    private String openingHours;

    @ValidPhoneNumber
    private String phoneNumber;
}
