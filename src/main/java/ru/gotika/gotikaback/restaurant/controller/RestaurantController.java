package ru.gotika.gotikaback.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.restaurant.dto.ChangeRequest;
import ru.gotika.gotikaback.restaurant.dto.RestaurantDto;
import ru.gotika.gotikaback.restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getRestaurants() {
        return ResponseEntity.ok(restaurantService.getRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDto));
    }

    @PatchMapping("/{id}/patch")
    public ResponseEntity<RestaurantDto> patchRestaurant(@PathVariable Long id, @RequestBody ChangeRequest changeRequest) {
        return ResponseEntity.ok(restaurantService.patchRestaurant(id, changeRequest));
    }


    @PatchMapping("/{id}/change-img")
    public ResponseEntity<RestaurantDto> changeImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(restaurantService.changeImage(id, file));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
