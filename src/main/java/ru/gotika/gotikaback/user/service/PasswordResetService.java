package ru.gotika.gotikaback.user.service;

public interface PasswordResetService {

    void sendResetCode(String email);
    void validateResetCode(String email, String code, String newPassword);
    void resetPassword(String email, String newPassword);
}
