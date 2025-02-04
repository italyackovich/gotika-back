package ru.gotika.gotikaback.auth.exception;

import java.io.Serial;
import java.io.Serializable;

public class TokenNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public TokenNotFoundException(final String message) {
        super(message);
    }

    public TokenNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
