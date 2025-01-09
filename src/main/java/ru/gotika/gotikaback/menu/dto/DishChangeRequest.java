package ru.gotika.gotikaback.menu.dto;

import lombok.Data;

@Data
public class DishChangeRequest {
    private String name;
    private String description;
    private Double price;
}
