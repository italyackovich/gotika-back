package ru.gotika.gotikaback.user.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.ChangeUserCredentials;
import ru.gotika.gotikaback.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    UserDto patchUser(Long id, ChangeAddress changeAddress);
    UserDto changeRole(Long id, ChangeRoleDto changeRoleDto);
    UserDto changeImage(Long id, MultipartFile image);
    UserDto changeUser(Long id, ChangeUserCredentials userCredentials);
    void deleteUser(Long id);
}
