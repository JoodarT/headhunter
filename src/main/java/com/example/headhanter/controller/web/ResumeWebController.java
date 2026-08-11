package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeWebController {

    private final ResumeService resumeService;
    private final UserService userService;

    @GetMapping
    public String getAllResumes(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("resumes", resumeService.searchResumes(search));
        } else {
            model.addAttribute("resumes", resumeService.getAllResumes());
        }
        return "resumes";
    }

    @GetMapping("/create")
    public String showCreateResumePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("resumeDto", new ResumeCreateDto());
        return "resume-create";
    }

    @PostMapping("/create")
    public String createResume(
            @ModelAttribute("resumeDto") ResumeCreateDto resumeDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        resumeDto.setUserId(currentUser.getId());

        resumeService.createResume(resumeDto);
        return "redirect:/resumes";
    }

    @GetMapping("/{id}/edit")
    public String showEditResumePage(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        var resume = resumeService.getResumeById(id);
        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        if (resume.getUserId() == null || !resume.getUserId().equals(currentUser.getId())) {
            return "redirect:/resumes?error=forbidden";
        }

        model.addAttribute("resume", resume);
        return "resume-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateResume(
            @PathVariable("id") Long id,
            @ModelAttribute("resumeDto") ResumeCreateDto resumeDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        resumeDto.setUserId(currentUser.getId());

        resumeService.updateResume(id, resumeDto, currentUser.getId());
        return "redirect:/resumes";
    }

    @PostMapping("/{id}/delete")
    public String deleteResume(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        resumeService.deleteResume(id, currentUser.getId());

        return "redirect:/resumes";
    }

    @GetMapping("/{id}")
    public String showResumeDetail(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        var resume = resumeService.getResumeById(id);
        model.addAttribute("resume", resume);

        boolean isOwner = false;
        if (userDetails != null) {
            User currentUser = userService.getUserByEmail(userDetails.getUsername());
            if (resume.getUserId() != null && resume.getUserId().equals(currentUser.getId())) {
                isOwner = true;
            }
        }
        model.addAttribute("isOwner", isOwner);

        return "resume-detail";
    }
}