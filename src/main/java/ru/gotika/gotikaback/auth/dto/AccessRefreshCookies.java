package ru.gotika.gotikaback.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
@AllArgsConstructor
public class AccessRefreshCookies {
    @NotNull(message = "AccessTokenCookie is required")
    ResponseCookie accessTokenCookie;

    @NotNull(message = "RefreshTokenCookie is required")
    ResponseCookie refreshTokenCookie;
}
