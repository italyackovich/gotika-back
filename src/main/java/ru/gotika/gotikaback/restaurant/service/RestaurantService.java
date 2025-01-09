package ru.gotika.gotikaback.restaurant.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.restaurant.dto.ChangeRequest;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getRestaurants();
    RestaurantDto getRestaurant(Long id);
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    RestaurantDto patchRestaurant(Long id, ChangeRequest changeRequest);
    RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto);
    RestaurantDto changeImage(Long id, MultipartFile file);
    void deleteRestaurant(Long id);
}
