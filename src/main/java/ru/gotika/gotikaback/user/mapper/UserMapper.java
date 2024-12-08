package ru.gotika.gotikaback.user.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.auth.dto.RegisterRequest;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
import ru.gotika.gotikaback.user.models.User;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
imports = {Role.class})
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", expression = "java(Role.ROLE_CLIENT)")
    User registerRequestToUser(RegisterRequest request);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", expression = "java(Role.ROLE_CLIENT)")
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "idToRestaurant")
    User userDtoToUser(UserDto userDto);

    UserDto optionalUserToUserDto(Optional<User> optionalUser);

    List<UserDto> usersToUserDtos(List<User> users);

    @Named("idToRestaurant")
    default Restaurant idToRestaurant(Long restaurantId) {
        if (restaurantId == null) return null;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}
