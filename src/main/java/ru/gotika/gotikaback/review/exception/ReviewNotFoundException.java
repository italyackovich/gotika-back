package ru.gotika.gotikaback.review.exception;

import java.io.Serial;
import java.io.Serializable;

public class ReviewNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
