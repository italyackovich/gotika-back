package ru.gotika.gotikaback.auth.exceptions;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(final String message) {
        super(message);
    }
}
