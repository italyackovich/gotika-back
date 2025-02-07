package ru.gotika.gotikaback.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.gotika.gotikaback.common.validator.ResetPasswordCodeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResetPasswordCodeValidator.class)
public @interface ValidResetPasswordCode {
    String message() default "Invalid reset password code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
