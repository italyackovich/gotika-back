package ru.gotika.gotikaback.menu.exception;

import java.io.Serial;
import java.io.Serializable;

public class DishCategoryNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public DishCategoryNotFoundException(String message) {
        super(message);
    }

    public DishCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
