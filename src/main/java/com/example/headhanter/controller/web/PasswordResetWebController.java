package com.example.headhanter.controller.web;

import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class PasswordResetWebController {

    private final UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String requestReset(@RequestParam("email") String email, Model model) {
        try {
            String token = userService.createPasswordResetToken(email);
            model.addAttribute("resetLink", "/reset-password?token=" + token);
        } catch (NoSuchElementException e) {
        }
        model.addAttribute("submitted", true);
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (!userService.isResetTokenValid(token)) {
            model.addAttribute("invalidToken", true);
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Пароли не совпадают");
            return "reset-password";
        }

        try {
            userService.resetPassword(token, password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("invalidToken", true);
            return "reset-password";
        }

        return "redirect:/login?passwordReset";
    }
}
