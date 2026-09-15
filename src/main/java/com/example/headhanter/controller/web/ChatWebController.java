package com.example.headhanter.controller.web;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ChatService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatWebController {

    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String showChat(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        model.addAttribute("chat", chatService.getChatView(id, currentUser.getId()));
        model.addAttribute("currentUserId", currentUser.getId());

        return "chat";
    }

    @PostMapping("/start")
    public String startChat(
            @RequestParam("resumeId") Long resumeId,
            @RequestParam("vacancyId") Long vacancyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        RespondedApplicant response = chatService.startChat(resumeId, vacancyId, currentUser.getId());

        return "redirect:/chat/" + response.getId();
    }
}
