package ru.gotika.gotikaback.user.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.user.dto.RegisterRequest;
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

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User optionalUserToUser(Optional<User> optionalUser);

    List<UserDto> usersToUserDtos(List<User> users);
}
