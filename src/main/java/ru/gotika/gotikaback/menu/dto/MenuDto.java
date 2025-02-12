package ru.gotika.gotikaback.menu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;

    @NotNull(message = "Restaurant id cannot be a null")
    private Long restaurantId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private List<DishCategoryDto> categoryList;
    private List<DishDto> dishList;
}
