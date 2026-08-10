//package com.example.headhanter.controller.web;
//
//import com.example.headhanter.models.User;
//import com.example.headhanter.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class ProfileWebController {
//
//    private final UserService userService;
//
//    @GetMapping("/profile")
//    public String showProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        if (userDetails == null) {
//            return "redirect:/login";
//        }
//
//        User currentUser = userService.getUserByEmail(userDetails.getUsername());
//        model.addAttribute("user", currentUser);
//
//        return "profile";
//    }
//}