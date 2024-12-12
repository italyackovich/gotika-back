package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.menu.dto.DishDto;
import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.menu.model.Menu;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {

    List<DishDto> dishListToDishDtoList(List<Dish> dishList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "menu", source = "menuId", qualifiedByName = "idToMenu")
    Dish dishDtoToDish(DishDto dishDto);

    @Mapping(target = "menuId", source = "menu.id")
    DishDto dishToDishDto(Dish dish);

    @Named("idToMenu")
    default Menu idToMenu(Long menuId) {
        if (menuId == null) return null;
        Menu menu = new Menu();
        menu.setId(menuId);
        return menu;
    }
}
