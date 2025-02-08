package ru.gotika.gotikaback.user.service;

import ru.gotika.gotikaback.user.dto.ChangePassword;
import ru.gotika.gotikaback.user.dto.ChangePasswordResponse;
import ru.gotika.gotikaback.user.dto.ConfirmResetPassword;
import ru.gotika.gotikaback.user.dto.RequestResetPassword;

public interface PasswordResetService {
    /**
     * Sends reset code to the user's email provided on the {@link RequestResetPassword}.
     *
     * @param requestResetPassword containing user's email.
     *                             Must not be a null.
     * @return a {@link ChangePasswordResponse} object that contains:
     * <ul>
     *     <li>{@code message}: a human-readable message describing the result of the operation.</li>
     *     <li>{@code success}: a boolean flag indicating whether the operation was successful.</li>
     * </ul>
     */
    ChangePasswordResponse sendResetCode(RequestResetPassword requestResetPassword);

    /**
     * Checks the password reset code sent to the user's email provided on the {@link ConfirmResetPassword}.
     *
     * @param confirmResetPassword containing user's email and password reset code.
     *                             Must not be a null.
     * @return a {@link ChangePasswordResponse} object that contains:
     * <ul>
     *     <li>{@code message}: a human-readable message describing the result of the operation.</li>
     *     <li>{@code success}: a boolean flag indicating whether the operation was successful.</li>
     * </ul>
     */
    ChangePasswordResponse validateResetCode(ConfirmResetPassword confirmResetPassword);

    /**
     * Changes user's password to new password provided on the {@link ChangePassword}.
     *
     * @param changePassword containing user's email and new password.
     * @return a {@link ChangePasswordResponse} object that contains:
     * <ul>
     *     <li>{@code message}: a human-readable message describing the result of the operation.</li>
     *     <li>{@code success}: a boolean flag indicating whether the operation was successful.</li>
     * </ul>
     */
    ChangePasswordResponse resetPassword(ChangePassword changePassword);
}
