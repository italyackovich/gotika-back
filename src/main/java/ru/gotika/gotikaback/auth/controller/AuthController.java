package ru.gotika.gotikaback.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.auth.dto.*;
import ru.gotika.gotikaback.auth.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try{
            AuthResponse authResponse = authService.register(request);
            AccessRefreshCookies cookieList = authResponse.getCookieList();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookieList.getAccessTokenCookie().toString())
                    .header(HttpHeaders.SET_COOKIE,cookieList.getRefreshTokenCookie().toString())
                    .body(authResponse.getUserDto());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authService.login(request);
            AccessRefreshCookies cookieList = authResponse.getCookieList();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookieList.getAccessTokenCookie().toString())
                    .header(HttpHeaders.SET_COOKIE,cookieList.getRefreshTokenCookie().toString())
                    .body(authResponse.getUserDto());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password is incorrect: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        AuthResponse authResponse = authService.refreshToken(request);
        AccessRefreshCookies cookieList = authResponse.getCookieList();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookieList.getAccessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE,cookieList.getRefreshTokenCookie().toString())
                .body(authResponse.getUserDto());
    }

}
