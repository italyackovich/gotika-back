package ru.gotika.gotikaback.auth.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class MissingCookieException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public MissingCookieException(final String message){
        super(message);
    }

    public MissingCookieException(final String message, final Throwable cause){
        super(message, cause);
    }
}
