package ru.gotika.gotikaback.menu.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.restaurant.model.Restaurant;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {DishMapper.class})
public interface MenuMapper {
    List<MenuDto> fromMenuListToMenuDtoList(List<Menu> menus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    Menu menuDtoToMenu(MenuDto menuDto);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    MenuDto menuToMenuDto(Menu menu);

    @Named("idToRestaurant")
    default Restaurant idToRestaurant(Long restaurantId) {
        if (restaurantId == null) return null;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}
