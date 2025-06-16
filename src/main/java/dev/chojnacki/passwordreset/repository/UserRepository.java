package dev.chojnacki.passwordreset.repository;

import dev.chojnacki.passwordreset.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByUsername(String username);
}
