package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.menu.model.Menu;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DishMapper.class, MapperUtil.class})
public interface MenuMapper {
    List<MenuDto> fromMenuListToMenuDtoList(List<Menu> menus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    Menu menuDtoToMenu(MenuDto menuDto);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    MenuDto menuToMenuDto(Menu menu);
}
