package com.example.headhanter.controller.api;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.User;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.ResponseService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;
    private final UserService userService;

    @GetMapping("/by-vacancy/{vacancyId}")
    public List<RespondedApplicant> getResponsesByVacancy(@PathVariable Long vacancyId) {
        return responseService.getResponsesByVacancyId(vacancyId);
    }

    @GetMapping("/my-vacancies/{resumeId}")
    public List<Vacancy> getMyRespondedVacancies(@PathVariable Long resumeId) {
        return responseService.getVacanciesRespondedByResume(resumeId);
    }

    @PostMapping
    public ResponseEntity<Void> respond(
            @RequestParam Long resumeId,
            @RequestParam Long vacancyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        responseService.respondToVacancy(resumeId, vacancyId, currentUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<RespondedApplicant> getAll() {
        return responseService.getAllResponses();
    }

    @PutMapping("/{id}/confirm")
    public String confirmApplicant(@PathVariable Long id, @RequestParam boolean status) {
        responseService.updateConfirmationStatus(id, status);
        return "Статус отклика успешно обновлен на: " + status;
    }
}