package ru.gotika.gotikaback.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.restaurant.dto.ChangeRestaurantDescDto;
import ru.gotika.gotikaback.restaurant.dto.RequestRestaurantDto;
import ru.gotika.gotikaback.restaurant.dto.ResponseRestaurantDto;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<ResponseRestaurantDto>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.getRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRestaurantDto> getRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }

    @PostMapping
    public ResponseEntity<ResponseRestaurantDto> createRestaurant(@RequestBody @Valid RequestRestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRestaurantDto> updateRestaurant(@PathVariable Long id, @RequestBody @Valid RequestRestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDto));
    }

    @PatchMapping("/{id}/data")
    public ResponseEntity<ResponseRestaurantDto> changeDesc(@PathVariable Long id, @RequestBody @Valid ChangeRestaurantDescDto changeRequest) {
        return ResponseEntity.ok(restaurantService.changeDesc(id, changeRequest));
    }


    @PatchMapping("/{id}/images")
    public ResponseEntity<ResponseRestaurantDto> changeImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(restaurantService.changeImage(id, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
