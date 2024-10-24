package ru.gotika.gotikaback.user_module.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${jwt.accessToken.cookie-name}")
    private String accessCookieName;

    @Value("${jwt.refreshToken.cookie-name}")
    private String refreshCookieName;

    public HttpCookie createAccessTokenCookie(String accessToken, long expiration) {
        return ResponseCookie.from(accessCookieName, accessToken)
                .maxAge(expiration)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
    }

    public HttpCookie createRefreshTokenCookie(String refreshToken, long expiration) {
        return ResponseCookie.from(refreshCookieName, refreshToken)
                .maxAge(expiration)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
    }

    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(accessCookieName, "")
                .httpOnly(false)
                .path("/")
                .build();
    }

    public HttpCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(refreshCookieName, "")
                .httpOnly(false)
                .path("/")
                .build();
    }
}
