package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.Mapper;
import ru.gotika.gotikaback.menu.dto.DishCategoryDto;
import ru.gotika.gotikaback.menu.model.DishCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishCategoryMapper {
    List<DishCategoryDto> categoryListToCategoryDtoList(List<DishCategory> categoryList);
    DishCategoryDto categoryToCategoryDto(DishCategory category);
    DishCategory categoryDtoToCategory(DishCategoryDto categoryDto);
}
