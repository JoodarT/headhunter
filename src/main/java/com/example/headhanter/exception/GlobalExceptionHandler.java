package com.example.headhanter.exception;

import com.example.headhanter.dto.ErrorResponseDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateKey(DuplicateKeyException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                "Конфликт данных",
                List.of("Пользователь с таким email уже существует")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


}