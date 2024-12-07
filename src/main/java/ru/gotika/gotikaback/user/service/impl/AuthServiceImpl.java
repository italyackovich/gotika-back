package ru.gotika.gotikaback.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.user.dto.AuthRequest;
import ru.gotika.gotikaback.user.dto.AuthResponse;
import ru.gotika.gotikaback.user.dto.RegisterRequest;
import ru.gotika.gotikaback.user.mapper.AuthMapper;
import ru.gotika.gotikaback.user.mapper.UserMapper;
import ru.gotika.gotikaback.user.models.Token;
import ru.gotika.gotikaback.user.models.User;
import ru.gotika.gotikaback.user.repository.TokenRepository;
import ru.gotika.gotikaback.user.repository.UserRepository;
import ru.gotika.gotikaback.user.service.AuthService;
import ru.gotika.gotikaback.user.service.JwtService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken);

        return authMapper.toAuthResponse(accessToken, refreshToken);
    }

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
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken);

        return authMapper.toAuthResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String accessToken) {
        Token token = Token.builder()
                .user(user)
                .token(accessToken)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findByUserIdAndRevokedFalse(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token ->
                token.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);
        if (email != null) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateAccessToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthResponse authResponse = authMapper.toAuthResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
