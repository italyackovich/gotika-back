package ru.gotika.gotikaback.restaurant.dto;

import lombok.Data;

@Data
public class ChangeRestaurantCred {
    private String name;
    private String address;
    private String openingHours;
    private String phoneNumber;
}
