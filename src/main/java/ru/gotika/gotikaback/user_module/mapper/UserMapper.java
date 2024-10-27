package ru.gotika.gotikaback.user_module.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.gotika.gotikaback.user_module.dto.RegisterRequest;
import ru.gotika.gotikaback.user_module.dto.UserDto;
import ru.gotika.gotikaback.user_module.enums.Role;
import ru.gotika.gotikaback.user_module.models.User;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
imports = {Role.class})
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(Role.ROLE_CLIENT)")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User registerRequestToUser(RegisterRequest request);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User optionalUserToUser(Optional<User> optionalUser);

    List<UserDto> usersToUserDtos(List<User> users);
}
