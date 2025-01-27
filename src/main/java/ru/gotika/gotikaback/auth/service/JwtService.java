package ru.gotika.gotikaback.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gotika.gotikaback.auth.dto.AccessRefreshCookies;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(UserDetails userDetails);

    String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    AccessRefreshCookies buildAccessRefreshTokenCookies(String accessToken, String refreshToken);

    boolean isTokenValid(String token, UserDetails userDetails);
}
