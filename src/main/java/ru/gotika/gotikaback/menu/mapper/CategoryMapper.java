package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.Mapper;
import ru.gotika.gotikaback.menu.dto.CategoryDto;
import ru.gotika.gotikaback.menu.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categoryList);
    CategoryDto categoryToCategoryDto(Category category);
    Category categoryDtoToCategory(CategoryDto categoryDto);
}
