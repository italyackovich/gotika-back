package ru.gotika.gotikaback.order.dto;

import jakarta.validation.constraints.NotNull;
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
    private Integer quantity;

    @NotNull(message = "Price cannot be a null")
    private Double price;

    @NotNull(message = "Order id cannot be a null")
    private Long orderId;

    @NotNull(message = "Dish id cannot be a null")
    private Long dishId;
    private DishDto dish;

}
