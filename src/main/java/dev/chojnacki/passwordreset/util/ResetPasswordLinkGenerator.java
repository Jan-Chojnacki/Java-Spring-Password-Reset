package dev.chojnacki.passwordreset.util;

import dev.chojnacki.passwordreset.model.PasswordResetToken;

public class ResetPasswordLinkGenerator {
    static public String generateResetPasswordLink(PasswordResetToken passwordResetToken) {
        return "http://localhost:8080/reset-password?token=" + passwordResetToken.getToken();
    }
}
