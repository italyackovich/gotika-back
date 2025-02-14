package ru.gotika.gotikaback.order.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> orderNotFoundException(final OrderNotFoundException e) {
        log.error("Occurred error: ", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Order not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = OrderItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> orderItemNotFoundException(final OrderItemNotFoundException e) {
        log.error("Occurred error: ", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Order item not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
