package ru.gotika.gotikaback.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gotika.gotikaback.auth.dto.AccessRefreshCookies;
import ru.gotika.gotikaback.user.model.User;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    /**
     * Extracts the user's email from the provided JWT token.
     * <p>
     * This method decodes the given JWT token and retrieves the subject claim,
     * which typically represents the user's email.
     *
     * @param token contains the JWT bearer token from which the email is to be extracted.
     *              Must not be null or empty.
     * @return a String containing user's email extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Extracts and resolves a specific claim from the provided JWT token.
     * <p>
     * This method decodes the given JWT token, extracts its claims, and applies the provided
     * {@code claimsResolver} function to retrieve a specific claim value.
     *
     * @param token containing the jwt bearer token from which the claim is to be extracted.
     *              Must not be null or empty.
     * @param claimsResolver containing a function that defines how to extract and transform the desire claim
     *                       from the token's claims.
     * @param <T> containing type of the claim to be extracted (e.g., String, Integer, etc.).
     * @return the resolved claim value extracted from the token using the provided resolver.
     * <p>
     * Example usage:
     * <pre>{@code
     *     String token = "your.jwt.token.here";
     *     String username = extractClaim(token, Claims::getSubject);
     * }</pre>
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Generates a new JWT access token for the specified user.
     * <p>
     * This method creates a compact, URL-safe JWT access token based on the provided user details.
     * The token includes standart claims such as subject (user's email) and expiration time.
     *
     * @param userDetails containing information about the user.
     *                    Must not be a null.
     * @return a String representing the generated JWT access token.
     */
    String generateAccessToken(UserDetails userDetails);

    /**
     * Generates a new JWT access token with additional custom claims for the specified user.
     * <p>
     * This method created a compact, URL-safe JWT access token that includes both standard claims
     * (such as subject and expiration) and additional custom claims provided in the map.
     *
     * @param extraClaims containing additional claims to include in the token (e.g., roles, permissions).
     *                    Can be empty but must not be null.
     * @param userDetails containing information about the user.
     * @return a String representing the generated JWT access token with custom claims.
     */
    String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Generates a new JWT refresh token for the specified user.
     * <p>
     * This method creates a compact, URL-safe JWT refresh token based on the provided user details.
     * Refresh tokens are typically used to obtain new access tokens after the original one expires.
     *
     * @param userDetails containing information about the user.
     * @return a String representing the generated JWT refresh token.
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * Builds a pair of HTTP cookies containing the access token and refresh token.
     * <p>
     * This method creates two secure, HTTP-only cookies: one for the access token and one for the refresh token.
     * These cookies can be used to securely store tokens on the client side.
     *
     * @param accessToken containing the JWT access token to be stored in the access cookie.
     *                    Must not be a null.
     * @param refreshToken containing the JWT access token to be stored in the refresh cookies.
     *                     Must not be a null.
     * @return an {@link AccessRefreshCookies} object containing the access and refresh cookies.
     */
    AccessRefreshCookies buildAccessRefreshTokenCookies(String accessToken, String refreshToken);

    /**
     * Validates the provided JWT token against the specified user details.
     * <p>
     * This method checks whether the token is valid by verifying its signature, ensuring it has not expired,
     * and confirming that the subject claims matches the email in the provided user details.
     *
     * @param token containing the JWT token to validate.
     *              Must not be a null.
     * @param userDetails containing information about the user.
     *                    Must not be a null.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Revokes all valid refresh tokens associated with the specified {@link User},
     * preventing any further use of those tokens.
     *
     * @param user the user whose refresh tokens should be revoked
     */
    void revokeAllUserTokens(User user);
}
