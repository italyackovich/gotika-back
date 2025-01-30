package ru.gotika.gotikaback.auth.service;

import ru.gotika.gotikaback.auth.exceptions.TokenNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gotika.gotikaback.auth.exceptions.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import ru.gotika.gotikaback.auth.dto.AuthResponse;
import ru.gotika.gotikaback.auth.dto.AuthRequest;
import ru.gotika.gotikaback.auth.dto.RegisterRequest;


public interface AuthService {

    /**
     * Registers a new user based on the provided request data,
     * then generates and returns authentication tokens (access and refresh).
     *
     * @param registerRequest contains user registration information (e.g., email, password)
     * @return an AuthResponse containing the user data and token cookies
     */
    AuthResponse register(RegisterRequest registerRequest);

    /**
     * Authenticates the user with the provided credentials,
     * and generates new authentication tokens (access and refresh).
     *
     * @param authRequest contains user authentication details (e.g., email, password)
     * @return an AuthResponse containing the user data and token cookies
     */
    AuthResponse login(AuthRequest authRequest);

    /**
     * Generate a new access token if the refresh token is still valid,
     * and returns an updated AuthResponse along with the token cookies
     *
     * @param httpServletRequest the HTTP request containing the refresh token cookie
     * @return an AuthResponse with the new access token and user data
     * @throws InvalidTokenException if the provided refresh token is invalid
     * @throws IllegalArgumentException if the email extracted from the token is null
     * @throws UsernameNotFoundException if no user was found for the given email
     * @throws TokenNotFoundException if the refresh token is not present in the repository
     */
    AuthResponse refreshToken(HttpServletRequest httpServletRequest);

}
