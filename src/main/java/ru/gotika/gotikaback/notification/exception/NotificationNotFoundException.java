package ru.gotika.gotikaback.notification.exception;

import java.io.Serial;
import java.io.Serializable;

public class NotificationNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
