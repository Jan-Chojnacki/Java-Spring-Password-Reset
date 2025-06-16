package dev.chojnacki.passwordreset.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private UUID token;

    @Column
    private Instant expiration;

    @ManyToOne
    private User user;
}
