package ru.gotika.gotikaback.auth.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("JWT expired: " + e.getMessage());
    }

    @ExceptionHandler(MissingCookieException.class)
    public ResponseEntity<String> handleMissingCookieException(MissingCookieException e) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Missing cookie: " + e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Invalid token: " + e.getMessage());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<String> handleTokenNotFoundException(TokenNotFoundException e) {
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Token not found: " + e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
    }
}
