package dev.chojnacki.passwordreset.service;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import dev.chojnacki.passwordreset.model.dto.ResetPasswordRequest;

public interface PasswordResetTokenService {
    PasswordResetToken generateResetPasswordToken(ResetPasswordRequest resetPasswordRequest);
}
