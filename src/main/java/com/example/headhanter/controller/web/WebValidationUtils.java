package com.example.headhanter.controller.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

final class WebValidationUtils {

    private WebValidationUtils() {
    }

    static String toErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining("; "));
    }
}
