package ru.gotika.gotikaback.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.restaurant.dto.ChangeRequest;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.mapper.RestaurantMapper;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final CloudinaryService cloudinaryService;

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
    public RestaurantDto patchRestaurant(Long id, ChangeRequest changeRequest) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setName(changeRequest.getName());
            restaurant.setAddress(changeRequest.getAddress());
            restaurant.setOpeningHours(changeRequest.getOpeningHours());
            restaurant.setPhoneNumber(changeRequest.getPhoneNumber());
            restaurantRepository.save(restaurant);
            return restaurantMapper.restaurantToRestaurantDto(restaurant);
        }).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Override
    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        return restaurantRepository.findById(id).map(restaurant -> {
            Restaurant updatedRestaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
            updatedRestaurant.setId(id);
            restaurantRepository.save(updatedRestaurant);
            return restaurantMapper.restaurantToRestaurantDto(updatedRestaurant);
        }).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Override
    public RestaurantDto changeImage(Long id, MultipartFile file) {
        return restaurantRepository.findById(id).map(r -> {
            r.setImageUrl(cloudinaryService.uploadFile(file));
            restaurantRepository.save(r);
            return restaurantMapper.restaurantToRestaurantDto(r);
        }).orElseThrow(null);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
