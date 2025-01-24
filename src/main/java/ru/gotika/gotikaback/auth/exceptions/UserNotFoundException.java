package ru.gotika.gotikaback.auth.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
