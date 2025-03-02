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

    @Test
    void createUser_ShouldSaveUserAndReturnDto() {
        UserDto inputDto = new UserDto();
        inputDto.setPassword("rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        User userToSave = new User();
        userToSave.setPassword("encodedPassword");

        when(userMapper.userDtoToUser(inputDto)).thenReturn(userToSave);

        User savedUser = new User();
        savedUser.setId(100L);
        savedUser.setPassword("encodedPassword");
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        UserDto outputDto = new UserDto();
        outputDto.setId(100L);
        outputDto.setPassword("encodedPassword");
        when(userMapper.userToUserDto(savedUser)).thenReturn(outputDto);

        UserDto result = userServiceImpl.createUser(inputDto);

        assertEquals(100L, result.getId());
        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder).encode("rawPassword");
        verify(userMapper).userDtoToUser(inputDto);
        verify(userRepository).save(userToSave);
        verify(userMapper).userToUserDto(savedUser);
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        Long userId = 1L;
        UserDto updateDto = new UserDto();
        updateDto.setPassword("newRawPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("oldEncodedPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setPassword("oldEncodedPassword");
        when(userMapper.userDtoToUser(updateDto)).thenReturn(updatedUser);

        User savedUser = new User();
        savedUser.setId(userId);
        savedUser.setPassword("oldEncodedPassword");
        when(userRepository.save(updatedUser)).thenReturn(savedUser);

        UserDto savedDto = new UserDto();
        savedDto.setId(userId);
        when(userMapper.userToUserDto(savedUser)).thenReturn(savedDto);

        UserDto result = userServiceImpl.updateUser(userId, updateDto);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userMapper).userDtoToUser(updateDto);
        verify(userRepository).save(updatedUser);
        verify(userMapper).userToUserDto(savedUser);
    }

    @Test
    void updateUser_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        UserDto updateDto = new UserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(userId, updateDto));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void deleteUser_ShouldDeleteUserById() {
        Long userId = 10L;

        userServiceImpl.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
