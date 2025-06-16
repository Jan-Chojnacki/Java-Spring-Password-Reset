package dev.chojnacki.passwordreset.service;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import dev.chojnacki.passwordreset.model.User;
import dev.chojnacki.passwordreset.model.dto.ResetPasswordRequest;
import dev.chojnacki.passwordreset.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository, UserService userService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userService = userService;
    }

    @Override
    public PasswordResetToken generateResetPasswordToken(ResetPasswordRequest resetPasswordRequest) {
        User user = userService.findUserByEmail(resetPasswordRequest.getEmail());
        if (user == null) throw new IllegalArgumentException();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(UUID.randomUUID());

        return passwordResetTokenRepository.save(passwordResetToken);
    }
}
