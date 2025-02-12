package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.dto.DishCategoryDto;
import ru.gotika.gotikaback.menu.exception.DishCategoryNotFoundException;
import ru.gotika.gotikaback.menu.mapper.DishCategoryMapper;
import ru.gotika.gotikaback.menu.model.DishCategory;
import ru.gotika.gotikaback.menu.repository.DishCategoryRepository;
import ru.gotika.gotikaback.menu.service.DishCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishCategoryServiceImpl implements DishCategoryService {

    private final DishCategoryMapper categoryMapper;
    private final DishCategoryRepository categoryRepository;

    @Override
    public List<DishCategoryDto> getAllCategories() {
        log.info("Get all dish categories");
        return categoryMapper.categoryListToCategoryDtoList(categoryRepository.findAll());
    }

    @Override
    public DishCategoryDto getCategoryById(Long id) {
        log.info("Get category by id: {}", id);
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new DishCategoryNotFoundException("Dish category with id: " + id + " not found")));
    }

    @Override
    public DishCategoryDto createCategory(DishCategoryDto categoryDto) {
        DishCategory category = categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDto));
        log.info("Create dish category: {}", category);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public DishCategoryDto updateCategory(Long id, DishCategoryDto categoryDto) {
        return categoryRepository.findById(id).map(category -> {
            DishCategory updatedCategory = categoryMapper.categoryDtoToCategory(categoryDto);
            updatedCategory.setId(category.getId());
            categoryRepository.save(updatedCategory);
            log.info("Update dish category: {}", updatedCategory);
            return categoryMapper.categoryToCategoryDto(updatedCategory);
        }).orElseThrow(() -> new DishCategoryNotFoundException("Dish category with id: " + id + " not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Delete dish category with id: {}", id);
        categoryRepository.deleteById(id);
    }
}
