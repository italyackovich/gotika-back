package ru.gotika.gotikaback.payment.exception;

import java.io.Serial;
import java.io.Serializable;

public class PaymentNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
