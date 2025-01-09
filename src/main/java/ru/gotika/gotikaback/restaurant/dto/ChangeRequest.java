package ru.gotika.gotikaback.restaurant.dto;

import lombok.Data;

@Data
public class ChangeRequest {
    private String name;
    private String address;
    private String openingHours;
    private String phoneNumber;
}
