package dev.chojnacki.passwordreset.service;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import dev.chojnacki.passwordreset.model.dto.RegisterUser;
import dev.chojnacki.passwordreset.model.dto.ResetPassword;
import dev.chojnacki.passwordreset.model.dto.ResetPasswordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PasswordResetTokenServiceTest {
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;

    @Autowired
    PasswordResetTokenServiceTest(PasswordResetTokenService passwordResetTokenService, UserService userService) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
    }

    @Test
    void validToken() {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setUsername("test1");
        registerUser.setPassword("Test1");
        registerUser.setEmail("test1@test.test");

        userService.registerUser(registerUser);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("test1@test.test");
        PasswordResetToken passwordResetToken =
                passwordResetTokenService.generateResetPasswordToken(resetPasswordRequest, 15);

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(passwordResetToken.getToken());
        resetPassword.setPassword("Qwerty123");

        assertDoesNotThrow(() -> userService.resetPassword(resetPassword));
    }

    @Test
    void invalidToken() {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(UUID.randomUUID());
        resetPassword.setPassword("Qwerty123");

        assertThrows(IllegalArgumentException.class, () -> userService.resetPassword(resetPassword));
    }

    @Test
    void expiredToken() {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setUsername("test2");
        registerUser.setPassword("Test2");
        registerUser.setEmail("test2@test.test");

        userService.registerUser(registerUser);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("test2@test.test");
        PasswordResetToken passwordResetToken =
                passwordResetTokenService.generateResetPasswordToken(resetPasswordRequest, -15);

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(passwordResetToken.getToken());
        resetPassword.setPassword("Qwerty123");

        assertThrows(IllegalArgumentException.class, () -> userService.resetPassword(resetPassword));
    }
}