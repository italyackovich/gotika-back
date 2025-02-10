package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.dto.DishCategoryDto;
import ru.gotika.gotikaback.menu.mapper.DishCategoryMapper;
import ru.gotika.gotikaback.menu.model.DishCategory;
import ru.gotika.gotikaback.menu.repository.DishCategoryRepository;
import ru.gotika.gotikaback.menu.service.DishCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishCategoryServiceImpl implements DishCategoryService {

    private final DishCategoryMapper categoryMapper;
    private final DishCategoryRepository categoryRepository;

    @Override
    public List<DishCategoryDto> getAllCategories() {
        return categoryMapper.categoryListToCategoryDtoList(categoryRepository.findAll());
    }

    @Override
    public DishCategoryDto getCategoryById(Long id) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public DishCategoryDto createCategory(DishCategoryDto categoryDto) {
        DishCategory category = categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDto));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public DishCategoryDto updateCategory(Long id, DishCategoryDto categoryDto) {
        return categoryRepository.findById(id).map(category -> {
            DishCategory updatedCategory = categoryMapper.categoryDtoToCategory(categoryDto);
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
