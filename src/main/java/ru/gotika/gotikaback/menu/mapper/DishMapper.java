package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.model.Dish;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface DishMapper {

    List<DishDto> dishListToDishDtoList(List<Dish> dishList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "menu", source = "menuId", qualifiedByName = "idToMenu")
    Dish dishDtoToDish(DishDto dishDto);

    @Mapping(target = "menuId", source = "menu.id")
    DishDto dishToDishDto(Dish dish);
}
