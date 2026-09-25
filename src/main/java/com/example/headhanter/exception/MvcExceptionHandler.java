package com.example.headhanter.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "com.example.headhanter.controller")
@RequiredArgsConstructor
public class MvcExceptionHandler {

    private final MessageSource messageSource;

    private String msg(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, HttpServletRequest request, Model model) {
        populateModel(model, 403, msg("error.accessDenied"), ex.getMessage(), request);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGlobalException(Exception ex, HttpServletRequest request, Model model) {
        populateModel(model, 500, msg("error.internal"),
                ex.getMessage() != null ? ex.getMessage() : msg("error.unexpected"), request);
        return "error";
    }

    private void populateModel(Model model, int status, String reason, String message, HttpServletRequest request) {
        model.addAttribute("status", status);
        model.addAttribute("reason", reason);
        model.addAttribute("message", message);

        Map<String, Object> details = new HashMap<>();
        details.put("serverName", request.getServerName());
        details.put("serverPort", request.getServerPort());
        details.put("requestURL", request.getRequestURI());

        model.addAttribute("details", details);
    }
}