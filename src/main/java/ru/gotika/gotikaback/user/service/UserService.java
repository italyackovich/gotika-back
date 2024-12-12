package ru.gotika.gotikaback.user.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    UserDto changeRole(Long id, ChangeRoleDto changeRoleDto);
    UserDto changeImage(Long id, MultipartFile image);
    void deleteUser(Long id);
}
