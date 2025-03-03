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
    void changeAddress_ShouldUpdateUserAddress_IfUserExisting() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ChangeAddress changeAddress = new ChangeAddress();
        changeAddress.setAddress("newAddress");
        user.setAddress(changeAddress.getAddress());

        User savedUser = new User();
        savedUser.setId(userId);
        savedUser.setAddress("newAddress");
        when(userRepository.save(user)).thenReturn(savedUser);

        UserDto savedDto = new UserDto();
        savedDto.setId(userId);
        savedDto.setAddress("newAddress");
        when(userMapper.userToUserDto(savedUser)).thenReturn(savedDto);

        UserDto result = userServiceImpl.changeAddress(userId, changeAddress);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(userMapper).userToUserDto(savedUser);
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
        User user = new User();
        user.setId(userId);
        user.setRole(Role.ROLE_CLIENT);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setRole(Role.ROLE_ADMIN);
        user.setRole(changeRoleDto.getRole());

        User savedUser = new User();
        savedUser.setId(userId);
        savedUser.setRole(Role.ROLE_ADMIN);

        when(userRepository.save(user)).thenReturn(savedUser);

        UserDto savedDto = new UserDto();
        savedDto.setId(userId);
        savedDto.setRole(Role.ROLE_ADMIN);

        when(userMapper.userToUserDto(savedUser)).thenReturn(savedDto);

        UserDto result = userServiceImpl.changeRole(userId, changeRoleDto);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(userMapper).userToUserDto(savedUser);
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

        User user = new User();
        user.setId(userId);
        user.setImageUrl("oldImageUrl");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String newImageUrl = "newImageUrl";

        when(cloudinaryService.uploadFile(image)).thenReturn(newImageUrl);
        when(userRepository.save(user)).thenReturn(user);

        UserDto expectedDto = new UserDto();
        expectedDto.setId(userId);

        when(userMapper.userToUserDto(user)).thenReturn(expectedDto);

        UserDto result = userServiceImpl.changeImage(userId, image);

        assertEquals(expectedDto, result);
        verify(userRepository).findById(userId);
        verify(cloudinaryService).uploadFile(image);
        verify(userRepository).save(user);
        verify(userMapper).userToUserDto(user);
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
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ChangeUserCredentials changeUserCredentials = new ChangeUserCredentials();
        changeUserCredentials.setFirstName("newFirstName");
        changeUserCredentials.setPhoneNumber("newPhoneNumber");
        changeUserCredentials.setLastName("newLastName");
        user.setFirstName(changeUserCredentials.getFirstName());
        user.setPhoneNumber(changeUserCredentials.getPhoneNumber());
        user.setLastName(changeUserCredentials.getLastName());

        User savedUser = new User();
        savedUser.setId(userId);
        savedUser.setFirstName("newFirstName");
        savedUser.setPhoneNumber("newPhoneNumber");
        savedUser.setLastName("newLastName");

        when(userRepository.save(user)).thenReturn(savedUser);

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setFirstName("newFirstName");
        userDto.setPhoneNumber("newPhoneNumber");
        userDto.setLastName("newLastName");
        when(userMapper.userToUserDto(savedUser)).thenReturn(userDto);

        UserDto result = userServiceImpl.changeCred(userId, changeUserCredentials);

        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(userMapper).userToUserDto(savedUser);
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
