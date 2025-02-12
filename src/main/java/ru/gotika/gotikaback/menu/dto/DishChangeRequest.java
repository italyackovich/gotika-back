package ru.gotika.gotikaback.menu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DishChangeRequest {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String description;

    @NotNull(message = "Price cannot be a null")
    private Double price;
}
