package dev.chojnacki.passwordreset.repository;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findPasswordResetTokenByToken(UUID token);
}
