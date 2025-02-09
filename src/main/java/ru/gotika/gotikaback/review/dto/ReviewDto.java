package ru.gotika.gotikaback.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gotika.gotikaback.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;

    @NotNull(message = "Rating cannot be a null")
    @Max(value = 5, message = "Rating must not exceed 5")
    @Min(value = 1, message = "Rating must be at least 1")
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "User id cannot be null")
    private Long restaurantId;
    private UserDto user;
}
