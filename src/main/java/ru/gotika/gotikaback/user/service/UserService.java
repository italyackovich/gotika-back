package ru.gotika.gotikaback.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gotika.gotikaback.user.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
