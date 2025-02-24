package ru.gotika.gotikaback.notification.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class NotificationExceptionHandler {

    @ExceptionHandler(value = NotificationNotFoundException.class)
    public ResponseEntity<Map<String, String>> notificationNotFoundException(final NotificationNotFoundException e) {
        log.error("Occurred error: ", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Notification not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
