package ru.gotika.gotikaback.restaurant.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantCred;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<ResponseRestaurantDto> getRestaurants();
    ResponseRestaurantDto getRestaurant(Long id);
    ResponseRestaurantDto createRestaurant(ResponseRestaurantDto restaurantDto);
    ResponseRestaurantDto changeCred(Long id, ChangeRestaurantCred changeRequest);
    ResponseRestaurantDto updateRestaurant(Long id, ResponseRestaurantDto restaurantDto);
    ResponseRestaurantDto changeImage(Long id, MultipartFile file);
    void deleteRestaurant(Long id);
}
