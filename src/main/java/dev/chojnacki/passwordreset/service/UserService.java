package dev.chojnacki.passwordreset.service;

import dev.chojnacki.passwordreset.model.User;
import dev.chojnacki.passwordreset.model.dto.RegisterUser;
import dev.chojnacki.passwordreset.model.dto.ResetPassword;

public interface UserService {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User resetPassword(ResetPassword resetPassword);

    User registerUser(RegisterUser registerUser);
}
