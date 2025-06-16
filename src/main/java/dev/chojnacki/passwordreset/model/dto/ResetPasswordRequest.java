package dev.chojnacki.passwordreset.model.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
}
