package ru.gotika.gotikaback.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.menu.dto.DishChangeRequest;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping
    public ResponseEntity<List<DishDto>> getDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PostMapping
    public ResponseEntity<DishDto> createDish(@RequestBody @Valid DishDto dishDto) {
        System.out.println(dishDto);
        return ResponseEntity.ok(dishService.createDish(dishDto));
    }

    @PatchMapping("/{id}/data")
    public ResponseEntity<DishDto> changeDishData(@PathVariable Long id, @RequestBody @Valid DishChangeRequest dishChangeRequest) {
        return  ResponseEntity.ok(dishService.changeDishData(id, dishChangeRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDto> updateDish(@PathVariable Long id, @RequestBody @Valid DishDto dishDto) {
        return ResponseEntity.ok(dishService.updateDish(id, dishDto));
    }

    @PatchMapping("/{id}/images")
    public ResponseEntity<DishDto> changeImg(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(dishService.changeImage(id, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}
