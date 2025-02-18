package ru.gotika.gotikaback.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;
import ru.gotika.gotikaback.auth.exception.MissingCookieException;

import java.util.Arrays;

@UtilityClass
public class CookieUtil {

    /**
     * Creates a new cookie based on the provided name,
     * value and value of expiration of cookie.
     *
     * @param cookieName contains name of cookie
     * @param cookieValue contains value of cookie
     * @param cookieExpiration contains value of expiration of cookie
     * @return a {@link ResponseCookie} containing the cookie
     */
    public ResponseCookie createCookie(String cookieName, String cookieValue, Long cookieExpiration) {
        return ResponseCookie.from(cookieName, cookieValue)
                .maxAge(cookieExpiration)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .build();
    }

    /**
     * Deletes the cookie based on the provided name of cookie.
     *
     * @param cookieName contains name of cookie
     */
    public void deleteCookie(String cookieName) {
        ResponseCookie.from(cookieName, "")
                .httpOnly(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    /**
     * Returns value of cookie based on the provided {@link HttpServletRequest} and name of cookie.
     *
     * @param request the HTTP request containing the cookies
     * @param cookieName contains name of cookie
     * @return a String containing the refresh or access token
     * @throws MissingCookieException if cookie is missing
     */
    public String getValueFromCookie(HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new MissingCookieException(cookieName));

    }
}
