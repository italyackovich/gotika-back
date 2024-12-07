package ru.gotika.gotikaback.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
import ru.gotika.gotikaback.user.models.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    UserDto changeRole(Long id, ChangeRoleDto changeRoleDto);
    void deleteUser(Long id);
}
