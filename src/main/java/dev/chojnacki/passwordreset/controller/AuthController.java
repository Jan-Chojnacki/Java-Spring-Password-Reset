package dev.chojnacki.passwordreset.controller;

import dev.chojnacki.passwordreset.model.PasswordResetToken;
import dev.chojnacki.passwordreset.model.dto.RegisterUser;
import dev.chojnacki.passwordreset.model.dto.ResetPassword;
import dev.chojnacki.passwordreset.model.dto.ResetPasswordRequest;
import dev.chojnacki.passwordreset.service.PasswordResetTokenService;
import dev.chojnacki.passwordreset.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.chojnacki.passwordreset.util.ResetPasswordLinkGenerator.generateResetPasswordLink;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public AuthController(UserService userService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new RegisterUser());
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterUser registerUser) {
        try {
            userService.registerUser(registerUser);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            return "/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("user", new ResetPasswordRequest());
        return "/forgot";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute("user") ResetPasswordRequest resetPasswordRequest, Model model) {
        try {
            PasswordResetToken token = passwordResetTokenService.generateResetPasswordToken(resetPasswordRequest);
            model.addAttribute("token", generateResetPasswordLink(token));
            return "/login";
        }catch (IllegalArgumentException e) {
            return "/forgot";
        }
    }

    @GetMapping("/reset-password")
    public String resetPassword(@ModelAttribute("token") UUID token, Model model) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(token);
        model.addAttribute("password", resetPassword);
        return "/reset";
    }

    @PostMapping("/reset-password")
    public String resetUserPassword(@ModelAttribute("password") ResetPassword resetPassword) {
        try {
            userService.resetPassword(resetPassword);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            return "/reset";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }
}
