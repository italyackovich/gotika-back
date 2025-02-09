package ru.gotika.gotikaback.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantCred;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.exception.RestaurantNotFoundException;
import ru.gotika.gotikaback.restaurant.mapper.RestaurantMapper;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<RestaurantDto> getRestaurants() {
        log.info("Get all restaurants");
        return restaurantMapper.restaurantListToRestaurantDtoList(restaurantRepository.findAll());
    }

    @Override
    public RestaurantDto getRestaurant(Long id) {
        log.info("Get restaurant with id: {}", id);
        return restaurantMapper.restaurantToRestaurantDto(restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found")));
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.save(restaurantMapper.restaurantDtoToRestaurant(restaurantDto));
        log.info("Created restaurant: {}", restaurant);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    @Override
    public RestaurantDto changeCred(Long id, ChangeRestaurantCred changeRequest) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setName(changeRequest.getName());
            restaurant.setAddress(changeRequest.getAddress());
            restaurant.setOpeningHours(changeRequest.getOpeningHours());
            restaurant.setPhoneNumber(changeRequest.getPhoneNumber());
            restaurantRepository.save(restaurant);
            log.info("Changed restaurant credentials: {}", restaurant);
            return restaurantMapper.restaurantToRestaurantDto(restaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
    }

    @Override
    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        return restaurantRepository.findById(id).map(restaurant -> {
            Restaurant updatedRestaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
            updatedRestaurant.setId(id);
            restaurantRepository.save(updatedRestaurant);
            log.info("Updated restaurant: {}", updatedRestaurant);
            return restaurantMapper.restaurantToRestaurantDto(updatedRestaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found"));
    }

    @Override
    public RestaurantDto changeImage(Long id, MultipartFile file) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setImageUrl(cloudinaryService.uploadFile(file));
            restaurantRepository.save(restaurant);
            log.info("Changed restaurant image: {}", restaurant);
            return restaurantMapper.restaurantToRestaurantDto(restaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found"));
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        log.info("Restaurant with id: {} deleted", id);
    }
}
