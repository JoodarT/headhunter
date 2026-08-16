package com.example.headhanter.exception;

import jakarta.servlet.http.HttpServletRequest;
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
public class MvcExceptionHandler {

    // "Пользователь ввёл неправильные данные" (несуществующий id, дубликат email и т.п.) —
    // не показываем отдельную страницу ошибки, а возвращаем на главную с сообщением
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
        populateModel(model, 403, "Отказ в доступе", ex.getMessage(), request);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGlobalException(Exception ex, HttpServletRequest request, Model model) {
        populateModel(model, 500, "Внутренняя ошибка сервера",
                ex.getMessage() != null ? ex.getMessage() : "Произошла непредвиденная ошибка", request);
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