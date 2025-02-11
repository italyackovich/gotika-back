package ru.gotika.gotikaback.common.util;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.gotika.gotikaback.menu.model.Dish;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.user.model.User;

@Mapper(componentModel = "spring")
public interface MapperUtil {

    @Named("idToRestaurant")
    default Restaurant idToRestaurant(Long restaurantId) {
        if (restaurantId == null) return null;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }

    @Named("idToMenu")
    default Menu idToMenu(Long menuId) {
        if (menuId == null) return null;
        Menu menu = new Menu();
        menu.setId(menuId);
        return menu;
    }

    @Named("idToUser")
    default User idToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("idToOrder")
    default Order idToOrder(Long orderId) {
        if (orderId == null) return null;
        Order order = new Order();
        order.setId(orderId);
        return order;
    }

    @Named("idToDish")
    default Dish idToDish(Long dishId) {
        if (dishId == null) return null;
        Dish dish = new Dish();
        dish.setId(dishId);
        return dish;
    }
}
