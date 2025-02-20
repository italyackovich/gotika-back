package ru.gotika.gotikaback.common.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class CodeGenerator {
    private final SecureRandom random = new SecureRandom();
    private final int CODE_LENGTH = 6;

    public String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
