package dev.chojnacki.passwordreset.service;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import dev.chojnacki.passwordreset.model.User;
import dev.chojnacki.passwordreset.model.dto.RegisterUser;
import dev.chojnacki.passwordreset.model.dto.ResetPassword;
import dev.chojnacki.passwordreset.repository.PasswordResetTokenRepository;
import dev.chojnacki.passwordreset.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User resetPassword(ResetPassword resetPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findPasswordResetTokenByToken(resetPassword.getToken());
        if (passwordResetToken == null) throw new IllegalArgumentException();
        if (passwordResetToken.getExpiration().isBefore(Instant.now())) throw new IllegalArgumentException();

        User user = passwordResetToken.getUser();

        user.setPassword(new BCryptPasswordEncoder().encode(resetPassword.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User registerUser(RegisterUser registerUser) {
        if (findUserByUsername(registerUser.getUsername()) != null) throw new IllegalArgumentException();

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(registerUser.getPassword()));
        user.setEmail(registerUser.getEmail());
        user.setRoles(Set.of("ROLE_USER"));

        return userRepository.save(user);
    }
}
