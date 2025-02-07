package ru.gotika.gotikaback.user.service;

import ru.gotika.gotikaback.user.dto.ChangePassword;
import ru.gotika.gotikaback.user.dto.ChangePasswordResponse;
import ru.gotika.gotikaback.user.dto.ConfirmResetPassword;
import ru.gotika.gotikaback.user.dto.RequestResetPassword;

public interface PasswordResetService {

    ChangePasswordResponse sendResetCode(RequestResetPassword requestResetPassword);
    ChangePasswordResponse validateResetCode(ConfirmResetPassword confirmResetPassword);
    ChangePasswordResponse resetPassword(ChangePassword changePassword);
}
