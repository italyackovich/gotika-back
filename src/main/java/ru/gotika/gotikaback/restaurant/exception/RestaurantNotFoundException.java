package ru.gotika.gotikaback.restaurant.exception;

import java.io.Serial;
import java.io.Serializable;

public class RestaurantNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
