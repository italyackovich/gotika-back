package ru.gotika.gotikaback.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Long menuId;

}
