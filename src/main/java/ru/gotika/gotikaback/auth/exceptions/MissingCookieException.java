package ru.gotika.gotikaback.auth.exceptions;

public class MissingCookieException extends RuntimeException{
    public MissingCookieException(final String message){
        super(message);
    }
}
