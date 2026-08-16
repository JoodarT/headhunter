package com.example.headhanter.controller.web;

import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "com.example.headhanter.controller.web")
@RequiredArgsConstructor
public class GlobalWebModelAttributes {

    private final UserService userService;

    @ModelAttribute("navUser")
    public User navUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        return userService.getUserByEmail(userDetails.getUsername());
    }
}
