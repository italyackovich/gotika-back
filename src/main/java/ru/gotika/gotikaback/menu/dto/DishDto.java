package ru.gotika.gotikaback.menu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String description;

    @NotNull(message = "Price cannot be a null")
    private Double price;
    private String imageUrl;

    @NotNull(message = "Menu id cannot be a null")
    private Long menuId;

}
