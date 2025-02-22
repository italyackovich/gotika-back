package ru.gotika.gotikaback.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantDescDto;
import ru.gotika.gotikaback.restaurant.dto.RequestRestaurantDto;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;
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
    public List<ResponseRestaurantDto> getRestaurants() {
        log.info("Get all restaurants");
        return restaurantRepository.findAll().stream().map(restaurantMapper::restaurantToResponseRestaurantDto).toList();
    }

    @Override
    public ResponseRestaurantDto getRestaurant(Long id) {
        log.info("Get restaurant with id: {}", id);
        return restaurantMapper.restaurantToResponseRestaurantDto(restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found")));
    }

    @Override
    public ResponseRestaurantDto createRestaurant(RequestRestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.save(restaurantMapper.requestRestaurantDtoToRestaurant(restaurantDto));
        log.info("Created restaurant: {}", restaurant);
        return restaurantMapper.restaurantToResponseRestaurantDto(restaurant);
    }

    @Override
    public ResponseRestaurantDto changeDesc(Long id, ChangeRestaurantDescDto changeRequest) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setName(changeRequest.getName());
            restaurant.setAddress(changeRequest.getAddress());
            restaurant.setOpeningHours(changeRequest.getOpeningHours());
            restaurant.setPhoneNumber(changeRequest.getPhoneNumber());
            restaurantRepository.save(restaurant);
            log.info("Changed restaurant credentials: {}", restaurant);
            return restaurantMapper.restaurantToResponseRestaurantDto(restaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
    }

    @Override
    public ResponseRestaurantDto updateRestaurant(Long id, RequestRestaurantDto restaurantDto) {
        return restaurantRepository.findById(id).map(restaurant -> {
            Restaurant updatedRestaurant = restaurantMapper.requestRestaurantDtoToRestaurant(restaurantDto);
            updatedRestaurant.setId(id);
            restaurantRepository.save(updatedRestaurant);
            log.info("Updated restaurant: {}", updatedRestaurant);
            return restaurantMapper.restaurantToResponseRestaurantDto(updatedRestaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found"));
    }

    @Override
    public ResponseRestaurantDto changeImage(Long id, MultipartFile file) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setImageUrl(cloudinaryService.uploadFile(file));
            restaurantRepository.save(restaurant);
            log.info("Changed restaurant image: {}", restaurant);
            return restaurantMapper.restaurantToResponseRestaurantDto(restaurant);
        }).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + id + " not found"));
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        log.info("Restaurant with id: {} deleted", id);
    }
}
