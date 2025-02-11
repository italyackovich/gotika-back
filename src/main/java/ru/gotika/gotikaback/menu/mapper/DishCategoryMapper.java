package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.menu.dto.DishCategoryDto;
import ru.gotika.gotikaback.menu.model.DishCategory;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface DishCategoryMapper {
    List<DishCategoryDto> categoryListToCategoryDtoList(List<DishCategory> categoryList);

    @Mapping(target = "menuId", source = "menu.id")
    DishCategoryDto categoryToCategoryDto(DishCategory category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "menu", source = "menuId", qualifiedByName = "idToMenu")
    DishCategory categoryDtoToCategory(DishCategoryDto categoryDto);
}
