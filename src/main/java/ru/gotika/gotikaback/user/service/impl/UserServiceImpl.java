package ru.gotika.gotikaback.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.user.dto.ChangeAddress;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.ChangeUserCredentials;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.exception.UserNotFoundException;
import ru.gotika.gotikaback.user.mapper.UserMapper;
import ru.gotika.gotikaback.user.model.User;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Get all users");
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        log.info("Get user with id {}", id);
        return userMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(userMapper.userDtoToUser(userDto));
        log.info("Created user {}", user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            User updatedUser = userMapper.userDtoToUser(userDto);
            updatedUser.setId(user.getId());
            updatedUser.setPassword(user.getPassword());
            userRepository.save(updatedUser);
            log.info("Updated user {}", updatedUser);
            return userMapper.userToUserDto(updatedUser);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserDto changeAddress(Long id, ChangeAddress changeAddress) {
        return userRepository.findById(id).map(user -> {
            user.setAddress(changeAddress.getAddress());
            userRepository.save(user);
            log.info("Changed user's address with id {} to {}", id, changeAddress);
            return userMapper.userToUserDto(user);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserDto changeRole(Long id, ChangeRoleDto changeRoleDto) {
        return userRepository.findById(id).map(user -> {
            user.setRole(changeRoleDto.getRole());
            userRepository.save(user);
            log.info("Changed user's role with id {} to {}", id, changeRoleDto);
            return userMapper.userToUserDto(user);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserDto changeImage(Long id, MultipartFile image) {
        return userRepository.findById(id).map(user -> {
            user.setImageUrl(cloudinaryService.uploadFile(image));
            userRepository.save(user);
            log.info("Changed user's image with id {}", id);
            return userMapper.userToUserDto(user);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserDto changeCred(Long id, ChangeUserCredentials userCredentials) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userCredentials.getFirstName());
            user.setLastName(userCredentials.getLastName());
            user.setPhoneNumber(userCredentials.getPhoneNumber());
            userRepository.save(user);
            log.info("Changed user's credentials with id {} to {}", id, userCredentials);
            return userMapper.userToUserDto(user);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleted user with id {}", id);
        userRepository.deleteById(id);
    }
}
