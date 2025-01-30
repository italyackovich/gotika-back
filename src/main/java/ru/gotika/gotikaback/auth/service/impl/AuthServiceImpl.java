package ru.gotika.gotikaback.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gotika.gotikaback.auth.dto.*;
import ru.gotika.gotikaback.auth.exceptions.InvalidTokenException;
import ru.gotika.gotikaback.auth.exceptions.TokenNotFoundException;
import ru.gotika.gotikaback.auth.util.CookieUtil;
import ru.gotika.gotikaback.user.dto.UserDto;
import ru.gotika.gotikaback.user.mapper.UserMapper;
import ru.gotika.gotikaback.auth.model.Token;
import ru.gotika.gotikaback.user.models.User;
import ru.gotika.gotikaback.auth.repository.TokenRepository;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.auth.service.AuthService;
import ru.gotika.gotikaback.auth.service.JwtService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.accessToken.expiration}")
    private Long accessExpiration;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;
    private final StringRedisTemplate stringRedisTemplate;

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        AccessRefreshCookies cookieList = jwtService.buildAccessRefreshTokenCookies(accessToken, refreshToken);

        stringRedisTemplate.opsForValue().set(user.getEmail(), accessToken, accessExpiration, TimeUnit.MILLISECONDS);
        saveUserToken(user, refreshToken);
        UserDto userDto = userMapper.userToUserDto(user);

        log.info("New user with id = {} registered. accessToken = {}, refreshToken = {}", user.getId(), accessToken, refreshToken);

        return new AuthResponse(cookieList, userDto);
    }

    @Transactional
    @Override
    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        AccessRefreshCookies cookieList = jwtService.buildAccessRefreshTokenCookies(accessToken, refreshToken);

        revokeAllUserTokens(user);

        stringRedisTemplate.opsForValue().set(user.getEmail(), accessToken, accessExpiration, TimeUnit.MILLISECONDS);
        saveUserToken(user, refreshToken);
        UserDto userDto = userMapper.userToUserDto(user);

        log.info("User with id = {} logged in", user.getId());

        return new AuthResponse(cookieList, userDto);
    }

    private void saveUserToken(User user, String refreshToken) {
        Token token = Token.builder()
                .token(refreshToken)
                .user(user)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
        log.info("Saved refreshToken: {} for user with id = {}", refreshToken, user.getId());
    }

    @Transactional
    public void revokeAllUserTokens(User user) {
        stringRedisTemplate.delete(user.getEmail());

        List<Token> validUserTokens = tokenRepository.findByUserIdAndIsRevokedFalse(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token ->
                token.setIsRevoked(true));
        tokenRepository.saveAll(validUserTokens);
        log.info("All user's tokens with id = {} is revoked", user.getId());
    }

    @Transactional
    @Override
    public AuthResponse refreshToken(HttpServletRequest request) {

        String refreshToken = cookieUtil.getValueFromCookie(request, "refreshTokenCookie");

        boolean isRefreshTokenValid = tokenRepository.findByToken(refreshToken)
                .map(token -> !token.getIsRevoked())
                .orElseThrow(() -> {
                    log.warn("Refresh token {} could not be found in the repository", refreshToken);
                    return new TokenNotFoundException("Refresh token " + refreshToken + " could not be found in the repository");
                });

        if (!isRefreshTokenValid) {
            throw new InvalidTokenException("Refresh token " + refreshToken + " is already revoked");
        }

        String email = jwtService.extractUsername(refreshToken);
        if (email == null) {
            log.error("Email is null");
            throw new IllegalArgumentException("Email cannot be null");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->{
                    log.warn("User not found for email: {}", email);
                    return new UsernameNotFoundException("Incorrect user's email -> " + email);
                });

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        if (!jwtService.isTokenValid(refreshToken, customUserDetails)) {
            log.warn("Token is invalid");
            throw new InvalidTokenException(refreshToken);
        }
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        revokeAllUserTokens(user);

        stringRedisTemplate.opsForValue().set(user.getEmail(), accessToken, accessExpiration, TimeUnit.MILLISECONDS);

        UserDto userDto = userMapper.userToUserDto(user);
        AccessRefreshCookies cookieList = jwtService.buildAccessRefreshTokenCookies(accessToken, refreshToken);
        log.info("Refreshing accessToken is success. New accessToken {}", accessToken);

        return new AuthResponse(cookieList, userDto);
    }
}
