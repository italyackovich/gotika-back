package ru.gotika.gotikaback.user.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.order.mapper.OrderMapper;
import ru.gotika.gotikaback.auth.dto.RegisterRequest;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
import ru.gotika.gotikaback.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring",
imports = {Role.class}, uses = {OrderMapper.class, MapperUtil.class})
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

    List<UserDto> usersToUserDtos(List<User> users);
}
