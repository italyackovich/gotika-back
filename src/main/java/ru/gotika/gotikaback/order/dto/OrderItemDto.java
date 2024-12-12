package ru.gotika.gotikaback.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.menu.dto.DishDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private Integer quantity;
    private Double price;
    private Long orderId;
    private Long dishId;
    private DishDto dish;

}
