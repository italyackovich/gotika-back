package ru.gotika.gotikaback.menu.exception;

import java.io.Serial;
import java.io.Serializable;

public class DishNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public DishNotFoundException(String message) {
        super(message);
    }

    public DishNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
