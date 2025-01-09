package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.controller.CategoryController;
import ru.gotika.gotikaback.menu.dto.CategoryDto;
import ru.gotika.gotikaback.menu.mapper.CategoryMapper;
import ru.gotika.gotikaback.menu.model.Category;
import ru.gotika.gotikaback.menu.repository.CategoryRepository;
import ru.gotika.gotikaback.menu.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.categoryListToCategoryDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDto));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return categoryRepository.findById(id).map(category -> {
            Category updatedCategory = categoryMapper.categoryDtoToCategory(categoryDto);
            updatedCategory.setId(category.getId());
            categoryRepository.save(updatedCategory);
            return categoryMapper.categoryToCategoryDto(updatedCategory);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
