package ru.gotika.gotikaback.menu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MenuExceptionHandler {

    @ExceptionHandler(DishCategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishCategoryNotFoundException(final DishCategoryNotFoundException e) {
        log.error("Dish category not found", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Dish category not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFoundException(final DishNotFoundException e) {
        log.error("Dish not found", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Dish not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMenuNotFoundException(final MenuNotFoundException e) {
        log.error("Menu not found", e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Menu not found");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
