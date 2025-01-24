package ru.gotika.gotikaback.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.gotika.gotikaback.auth.dto.AuthResponse;
import ru.gotika.gotikaback.auth.dto.AuthRequest;
import ru.gotika.gotikaback.auth.dto.RegisterRequest;

import java.io.IOException;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    AuthResponse refreshToken(HttpServletRequest httpServletRequest);

}
