package ru.gotika.gotikaback.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.ChangeUserCredentials;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.enums.Role;
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
    void getAllUsers_ShouldReturnListOfUserDto() {
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

        when(userRepository.save(userToSave)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setPassword("encodedPassword");
            user.setId(100L);
            return user;
        });

        when(userMapper.userToUserDto(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setPassword(user.getPassword());
            userDto.setId(user.getId());
            return userDto;
        });

        UserDto result = userServiceImpl.createUser(inputDto);

        assertEquals(100L, result.getId());
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder).encode("rawPassword");
        verify(userMapper).userDtoToUser(inputDto);
        verify(userRepository).save(userToSave);
        verify(userMapper).userToUserDto(userToSave);
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        Long userId = 1L;
        UserDto updateDto = new UserDto();
        updateDto.setPassword("newRawPassword");
        updateDto.setFirstName("newFirstName");
        updateDto.setLastName("newLastName");

        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setFirstName("newFirstName");
        updatedUser.setLastName("newLastName");
        updatedUser.setPassword("newRawPassword");
        when(userMapper.userDtoToUser(updateDto)).thenReturn(updatedUser);

        when(passwordEncoder.encode("newRawPassword")).thenReturn("newEncodedPassword");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(existingUser.getId());
            return user;
        });

        when(userMapper.userToUserDto(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            return userDto;
        });

        UserDto result = userServiceImpl.updateUser(userId, updateDto);

        assertEquals(userId, result.getId());
        assertEquals("newFirstName", result.getFirstName());
        assertEquals("newLastName", result.getLastName());

        verify(userRepository).findById(userId);
        verify(userMapper).userDtoToUser(updateDto);
        verify(userRepository).save(updatedUser);
        verify(userMapper).userToUserDto(updatedUser);
        verify(passwordEncoder).encode("newRawPassword");
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
    void changeAddress_ShouldUpdateUserAddress_IfUserExisting() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        ChangeAddress changeAddress = new ChangeAddress();
        changeAddress.setAddress("newAddress");
        existingUser.setAddress(changeAddress.getAddress());

        when(userRepository.save(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(existingUser.getId());
            return user;
        });

        when(userMapper.userToUserDto(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setAddress(user.getAddress());
            return userDto;
        });

        UserDto result = userServiceImpl.changeAddress(userId, changeAddress);

        assertEquals(userId, result.getId());
        assertEquals("newAddress", result.getAddress());

        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).userToUserDto(existingUser);
    }

    @Test
    void changeAddress_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        ChangeAddress changeAddress = new ChangeAddress();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.changeAddress(userId, changeAddress));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void changeRole_ShouldUpdateUserRole_IfUserExisting() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setRole(Role.ROLE_CLIENT);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setRole(Role.ROLE_ADMIN);
        existingUser.setRole(changeRoleDto.getRole());

        when(userRepository.save(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(existingUser.getId());
            user.setRole(Role.ROLE_ADMIN);
            return user;
        });

        when(userMapper.userToUserDto(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setRole(user.getRole());
            return userDto;
        });

        UserDto result = userServiceImpl.changeRole(userId, changeRoleDto);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).userToUserDto(existingUser);
    }

    @Test
    void changeRole_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.changeRole(userId, changeRoleDto));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void changeImage_ShouldUpdateUserImage_IfUserExisting() {
        Long userId = 1L;
        MultipartFile image = mock(MultipartFile.class);

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setImageUrl("oldImageUrl");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        String newImageUrl = "newImageUrl";

        when(cloudinaryService.uploadFile(image)).thenReturn(newImageUrl);
        when(userRepository.save(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(existingUser.getId());
            user.setImageUrl(newImageUrl);
            return user;
        });

        when(userMapper.userToUserDto(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setImageUrl(user.getImageUrl());
            return userDto;
        });

        UserDto result = userServiceImpl.changeImage(userId, image);

        assertEquals("newImageUrl", result.getImageUrl());

        verify(userRepository).findById(userId);
        verify(cloudinaryService).uploadFile(image);
        verify(userRepository).save(existingUser);
        verify(userMapper).userToUserDto(existingUser);
    }

    @Test
    void changeImage_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        User user = new User();
        user.setId(userId);
        MultipartFile image = mock(MultipartFile.class);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.changeImage(userId, image));
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void changeCred_ShouldUpdateUserCred_IfUserExisting() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("oldFirstName");
        existingUser.setPhoneNumber("oldPhoneNumber");
        existingUser.setLastName("oldLastName");


        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        ChangeUserCredentials changeUserCredentials = new ChangeUserCredentials();
        changeUserCredentials.setFirstName("newFirstName");
        changeUserCredentials.setPhoneNumber("newPhoneNumber");
        changeUserCredentials.setLastName("newLastName");

        when(userRepository.save(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(existingUser.getId());
            user.setFirstName(changeUserCredentials.getFirstName());
            user.setPhoneNumber(changeUserCredentials.getPhoneNumber());
            user.setLastName(changeUserCredentials.getLastName());
            return user;
        });

        when(userMapper.userToUserDto(existingUser)).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setLastName(user.getLastName());
            return userDto;
        });

        UserDto result = userServiceImpl.changeCred(userId, changeUserCredentials);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verify(userMapper).userToUserDto(existingUser);
    }

    @Test
    void changeCred_ShouldThrowException_IfUserNotFound() {
        Long userId = 999L;
        User user = new User();
        user.setId(userId);
        ChangeUserCredentials userCredentials = new ChangeUserCredentials();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.changeCred(userId, userCredentials));
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
