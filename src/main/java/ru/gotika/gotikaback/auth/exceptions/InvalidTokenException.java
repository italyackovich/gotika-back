package ru.gotika.gotikaback.auth.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(final String message) {
        super(message);
    }
}
