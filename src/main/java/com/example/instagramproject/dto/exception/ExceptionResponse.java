package com.example.instagramproject.dto.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private HttpStatus httpStatus;
    private String exceptionName;
    private String message;
    private LocalDateTime timestamp;

}