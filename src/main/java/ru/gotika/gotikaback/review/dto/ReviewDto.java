package ru.gotika.gotikaback.review.dto;

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
    private Float rating;
    private String comment;
    private LocalDateTime createdAt;
    private Long userId;
    private Long restaurantId;
    private UserDto user;
}
