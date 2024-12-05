package ru.gotika.gotikaback.restaurant.service;

import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getRestaurants();
    RestaurantDto getRestaurant(Long id);
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto);
    void deleteRestaurant(Long id);
}
