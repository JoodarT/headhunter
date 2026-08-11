package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileWebController {

    private final UserService userService;

    @GetMapping
    public String showProfile(Authentication authentication, Model model) {
        User user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Authentication authentication, Model model) {
        User user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/edit/{userId}")
    public String updateProfile(
            @PathVariable Long userId,
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "profile-edit";
        }
        userService.updateUser(userId, userDto);
        return "redirect:/profile";
    }

    @PostMapping("/avatar/{userId}")
    public String uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        log.info("Запрос на загрузку аватарки для userId: {}, имя файла: {}", userId, file.getOriginalFilename());
        userService.uploadAvatar(userId, file);
        log.info("Аватар успешно обновлен для userId: {}", userId);
        return "redirect:/profile";
    }
}