package dev.chojnacki.passwordreset.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResetPassword {
    private UUID token;
    private String password;
}
