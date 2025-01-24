package ru.gotika.gotikaback.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
@AllArgsConstructor
public class AccessRefreshCookies {
    ResponseCookie accessTokenCookie;
    ResponseCookie refreshTokenCookie;
}
