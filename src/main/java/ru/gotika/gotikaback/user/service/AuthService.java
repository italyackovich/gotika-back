package ru.gotika.gotikaback.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.gotika.gotikaback.user.dto.AuthRequest;
import ru.gotika.gotikaback.user.dto.AuthResponse;
import ru.gotika.gotikaback.user.dto.RegisterRequest;

import java.io.IOException;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    void refreshToken(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException;

}
