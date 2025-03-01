package ru.gotika.gotikaback.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
import ru.gotika.gotikaback.user.mapper.UserMapper;
import ru.gotika.gotikaback.user.model.User;
import ru.gotika.gotikaback.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void getALlUsers_ShouldReturnListOfUserDto() {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> userDtoList = Arrays.asList(new UserDto(), new UserDto());
        when(userMapper.usersToUserDtos(userList)).thenReturn(userDtoList);

        List<UserDto> result = userServiceImpl.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verify(userMapper).usersToUserDtos(userList);
    }

    @Test
    void getUser_ShouldReturnUserDto_IfUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto();
        userDto.setId(userId);

        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userServiceImpl.getUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userMapper).userToUserDto(user);
    }

    @Test
    void getUser_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser(userId));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userMapper);
    }
}
