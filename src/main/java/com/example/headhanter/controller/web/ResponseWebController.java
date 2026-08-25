package com.example.headhanter.controller.web;

import com.example.headhanter.dto.response.RespondedApplicantResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ResponseService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseWebController {

    private final ResponseService responseService;
    private final UserService userService;

    @GetMapping
    public String myResponses(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        String role = currentUser.getRole() != null ? currentUser.getRole().getRole() : null;
        boolean isEmployer = "EMPLOYER".equals(role);

        List<RespondedApplicantResponseDto> responses = isEmployer
                ? responseService.getResponsesByEmployerId(currentUser.getId())
                : responseService.getResponsesByApplicantUserId(currentUser.getId());

        model.addAttribute("responses", responses);
        model.addAttribute("isEmployer", isEmployer);

        return "responses/my-responses";
    }
}
