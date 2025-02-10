package ru.gotika.gotikaback.menu.service;

import ru.gotika.gotikaback.menu.dto.DishCategoryDto;

import java.util.List;

public interface DishCategoryService {
    List<DishCategoryDto> getAllCategories();
    DishCategoryDto getCategoryById(Long id);
    DishCategoryDto createCategory(DishCategoryDto categoryDto);
    DishCategoryDto updateCategory(Long id, DishCategoryDto categoryDto);
    void deleteCategory(Long id);
}
