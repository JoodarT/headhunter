package com.example.headhanter.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "com.example.headhanter.controller")
public class MvcExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoSuchElementException ex, HttpServletRequest request, Model model) {
        populateModel(model, 404, "Страница или ресурс не найдены", ex.getMessage(), request);
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalArgumentException ex, HttpServletRequest request, Model model) {
        populateModel(model, 400, "Некорректный запрос", ex.getMessage(), request);
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