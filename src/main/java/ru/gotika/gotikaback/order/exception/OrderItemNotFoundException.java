package ru.gotika.gotikaback.order.exception;

import java.io.Serial;
import java.io.Serializable;

public class OrderItemNotFoundException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
