package ru.gotika.gotikaback.auth.exception;

import java.io.Serial;
import java.io.Serializable;

public class InvalidTokenException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
