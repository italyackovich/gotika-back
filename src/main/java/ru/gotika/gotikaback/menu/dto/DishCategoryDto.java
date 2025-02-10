package ru.gotika.gotikaback.menu.dto;

import lombok.Data;

@Data
public class DishCategoryDto {

    private Long id;
    private String name;
    private Long menuId;
}
