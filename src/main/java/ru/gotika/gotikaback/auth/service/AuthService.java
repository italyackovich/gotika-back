package ru.gotika.gotikaback.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.gotika.gotikaback.auth.dto.AuthResponse;
import ru.gotika.gotikaback.auth.dto.AuthRequest;
import ru.gotika.gotikaback.auth.dto.RegisterRequest;
import ru.gotika.gotikaback.user.models.User;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    AuthResponse refreshToken(HttpServletRequest httpServletRequest);
    void revokeAllUserTokens(User user);

}
