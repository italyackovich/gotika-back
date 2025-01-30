package ru.gotika.gotikaback.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import ru.gotika.gotikaback.auth.exceptions.MissingCookieException;

import java.util.Arrays;

@Component
public class CookieUtil {

    public ResponseCookie createCookie(String cookieName, String cookieValue, Long cookieExpiration) {
        return ResponseCookie.from(cookieName, cookieValue)
                .maxAge(cookieExpiration)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .build();
    }

    public void deleteCookie(String cookieName) {
        ResponseCookie.from(cookieName, "")
                .httpOnly(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    public String getValueFromCookie(HttpServletRequest request, String cookieName) {
        try {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseThrow();
        } catch(NullPointerException ex) {
            throw new MissingCookieException(cookieName);
        }

    }
}
