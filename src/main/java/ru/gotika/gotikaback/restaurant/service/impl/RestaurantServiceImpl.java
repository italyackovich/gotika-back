package ru.gotika.gotikaback.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.mapper.RestaurantMapper;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;
import ru.gotika.gotikaback.user.models.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantDto> getRestaurants() {
        return restaurantMapper.restaurantListToRestaurantDtoList(restaurantRepository.findAll());
    }

    @Override
    public RestaurantDto getRestaurant(Long id) {
        return restaurantMapper.restaurantToRestaurantDto(restaurantRepository.findById(id).orElse(null));
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        restaurantRepository.save(restaurantMapper.restaurantDtoToRestaurant(restaurantDto));
        return restaurantDto;
    }

    @Override
    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        restaurantRepository.findById(id).ifPresent(restaurant -> {
            restaurantRepository.save(restaurantMapper.restaurantDtoToRestaurant(restaurantDto));
        });
        return restaurantDto;
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
