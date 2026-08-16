package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        String role = currentUser.getRole() != null ? currentUser.getRole().getRole() : null;

        if ("EMPLOYER".equals(role)) {
            return "redirect:/resumes";
        }
        if ("APPLICANT".equals(role)) {
            return "redirect:/vacancies";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Validated({Default.class, UserDto.OnCreate.class}) @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", WebValidationUtils.toErrorMessage(bindingResult));
            return "register";
        }
        userService.createUser(userDto);
        return "redirect:/login?registered";
    }
}