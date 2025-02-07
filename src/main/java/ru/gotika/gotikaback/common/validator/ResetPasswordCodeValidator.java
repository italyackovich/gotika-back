package ru.gotika.gotikaback.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.gotika.gotikaback.common.annotation.ValidResetPasswordCode;

public class ResetPasswordCodeValidator implements ConstraintValidator<ValidResetPasswordCode, String> {
    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (code == null || code.length() != 6) {
            return false;
        }
        return code.chars().allMatch(Character::isDigit);
    }
}
