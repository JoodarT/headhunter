package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.Role;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;

    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        UserDto userDto = new UserDto();
        userDto.setEmail(currentUser.getEmail());
        userDto.setName(currentUser.getName());
        userDto.setPhone(currentUser.getPhone());
        if (currentUser.getRole() != null) {
            userDto.setAccountType(Role.valueOf(currentUser.getRole().getRole()));
        }

        model.addAttribute("userDto", userDto);
        model.addAttribute("userId", currentUser.getId());
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", currentUser.getId());
            model.addAttribute("error", WebValidationUtils.toErrorMessage(bindingResult));
            return "profile-edit";
        }

        User updatedUser = userService.updateUser(currentUser.getId(), userDto);

        if (userDto.getEmail() != null && !currentUser.getEmail().equals(updatedUser.getEmail())) {
            UserDetails updatedUserDetails = org.springframework.security.core.userdetails.User
                    .withUsername(updatedUser.getEmail())
                    .password(updatedUser.getPassword())
                    .authorities(userDetails.getAuthorities())
                    .build();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            updatedUserDetails,
                            updatedUser.getPassword(),
                            userDetails.getAuthorities()
                    )
            );
        }

        return "redirect:/profile";
    }

    @PostMapping("/avatar/{userId}")
    public String uploadAvatar(
            @PathVariable("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        if (currentUser.getId().equals(userId)) {
            userService.uploadAvatar(userId, file);
        }

        return "redirect:/profile";
    }
}