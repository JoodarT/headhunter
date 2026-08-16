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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // если только что произошёл редирект с ошибкой (см. MvcExceptionHandler),
        // показываем главную с сообщением вместо автоматического перехода дальше
        if (userDetails == null || model.containsAttribute("error")) {
            return "main";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        String role = currentUser.getRole() != null ? currentUser.getRole().getRole() : null;

        if ("EMPLOYER".equals(role)) {
            return "redirect:/resumes";
        }
        if ("APPLICANT".equals(role)) {
            return "redirect:/vacancies";
        }
        return "main";
    }

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String registered,
            @RequestParam(required = false) String logout,
            @RequestParam(required = false) String roleMismatch,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (registered != null) {
            model.addAttribute("registered", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        if (roleMismatch != null) {
            model.addAttribute("roleMismatch", true);
        }
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