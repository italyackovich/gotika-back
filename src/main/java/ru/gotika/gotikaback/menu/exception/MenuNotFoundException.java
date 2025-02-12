package ru.gotika.gotikaback.menu.exception;

import java.io.Serial;
import java.io.Serializable;

public class MenuNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
