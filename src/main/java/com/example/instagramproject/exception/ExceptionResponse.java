package com.example.instagramproject.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(
        HttpStatus httpStatus,
        String message,
        String exceptionName,
        LocalDateTime timestamp

) {
}