package ru.gotika.gotikaback.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gotika.gotikaback.common.service.CloudinaryService;
import ru.gotika.gotikaback.user.dto.ChangeRoleDto;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.mapper.UserMapper;
import ru.gotika.gotikaback.user.models.User;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.save(userMapper.userDtoToUser(userDto));
        });
        return userDto;
    }

    @Override
    public UserDto changeRole(Long id, ChangeRoleDto changeRoleDto) {
        return userRepository.findById(id).map(user -> {
            user.setRole(changeRoleDto.getRole());
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }).orElseThrow(null);
    }

    @Override
    public UserDto changeImage(Long id, MultipartFile image) {
        return userRepository.findById(id).map(user -> {
            user.setImageUrl(cloudinaryService.uploadFile(image));
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }).orElseThrow(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
