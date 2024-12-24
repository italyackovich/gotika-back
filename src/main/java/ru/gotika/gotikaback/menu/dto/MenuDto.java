package ru.gotika.gotikaback.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    private Long restaurantId;
    private String name;
    private List<CategoryDto> categoryList;
    private List<DishDto> dishList;
}
