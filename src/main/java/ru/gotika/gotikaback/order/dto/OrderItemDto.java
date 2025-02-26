package ru.gotika.gotikaback.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.menu.dto.DishDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;

    @NotNull(message = "Quantity cannot be a null")
    @PositiveOrZero(message = "Quantity must be at least 0")
    private Integer quantity;

    @NotNull(message = "Price cannot be a null")
    @PositiveOrZero(message = "Price id must be at least 0")
    private Double price;

    @NotNull(message = "Order id cannot be a null")
    @PositiveOrZero(message = "Order id must be at least 0")
    private Long orderId;

    @NotNull(message = "Dish id cannot be a null")
    @PositiveOrZero(message = "Dish id must be at least 0")
    private Long dishId;
    private DishDto dish;

}
