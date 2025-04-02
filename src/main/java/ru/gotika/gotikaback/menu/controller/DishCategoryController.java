package ru.gotika.gotikaback.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.menu.dto.DishCategoryDto;
import ru.gotika.gotikaback.menu.service.DishCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dish-categories")
@RequiredArgsConstructor
public class DishCategoryController {

    private final DishCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<DishCategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishCategoryDto> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<DishCategoryDto> createCategory(@RequestBody @Valid DishCategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishCategoryDto> updateCategory(@PathVariable Long id, @RequestBody @Valid DishCategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
