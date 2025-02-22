package ru.gotika.gotikaback.restaurant.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantDescDto;
import ru.gotika.gotikaback.restaurant.dto.RequestRestaurantDto;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<ResponseRestaurantDto> getRestaurants();
    ResponseRestaurantDto getRestaurant(Long id);
    ResponseRestaurantDto createRestaurant(RequestRestaurantDto restaurantDto);
    ResponseRestaurantDto changeDesc(Long id, ChangeRestaurantDescDto changeRequest);
    ResponseRestaurantDto updateRestaurant(Long id, RequestRestaurantDto restaurantDto);
    ResponseRestaurantDto changeImage(Long id, MultipartFile file);
    void deleteRestaurant(Long id);
}
