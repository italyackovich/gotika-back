package ru.gotika.gotikaback.user_module.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.user_module.dto.UserDto;
import ru.gotika.gotikaback.user_module.mapper.UserMapper;
import ru.gotika.gotikaback.user_module.models.User;
import ru.gotika.gotikaback.user_module.repository.UserRepository;
import ru.gotika.gotikaback.user_module.service.JwtService;
import ru.gotika.gotikaback.user_module.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<UserDto> getAllUsers() {
        System.out.println(userMapper.usersToUserDtos(userRepository.findAll()));
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userRepository.save(userMapper.userDtoToUser(userDto));
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.save(userMapper.userDtoToUser(userDto));
        });
        return userMapper.userToUserDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
