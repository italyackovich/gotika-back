package ru.gotika.gotikaback.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.auth.dto.*;
import ru.gotika.gotikaback.auth.service.AuthService;
import ru.gotika.gotikaback.user.dto.UserDto;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Endpoints for user registration, login, and token refresh")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user based on the provided registration data and returns authentication tokens as cookies.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid registration data"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User with the same email already exists"
                    )
            }
    )
    @PostMapping("/accounts")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        AccessRefreshCookies cookieList = authResponse.getCookieList();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookieList.getAccessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE,cookieList.getRefreshTokenCookie().toString())
                .body(authResponse.getUserDto());
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user based on the provided credentials and returns authentication tokens as cookies.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid credentials"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access"
                    )
            }
    )
    @PostMapping("/sessions")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        AccessRefreshCookies cookieList = authResponse.getCookieList();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookieList.getAccessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE,cookieList.getRefreshTokenCookie().toString())
                .body(authResponse.getUserDto());
    }

    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token and returns it along with user data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Access token refreshed successfully",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid or missing refresh token"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized access"
                    )
            }
    )
    @PostMapping("/tokens")
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
