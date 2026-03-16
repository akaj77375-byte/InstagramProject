package com.example.instagramproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ExceptionResponse buildResponse(Exception e, HttpStatus status) {
        return ExceptionResponse.builder()
                .httpStatus(status)
                .exceptionName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFound(NotFoundException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleAlreadyExists(AlreadyExistsException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserMismatchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleUserMismatch(UserMismatchException e) {
        return buildResponse(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleBadCredentials(BadCredentialsException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .exceptionName(e.getClass().getSimpleName())
                .message("Неверный логин или пароль")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleExpiredJwt(ExpiredJwtException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .exceptionName(e.getClass().getSimpleName())
                .message("JWT токен истёк")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleJwt(JwtException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .exceptionName(e.getClass().getSimpleName())
                .message("Неверный JWT токен")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccessDenied(AccessDeniedException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionName(e.getClass().getSimpleName())
                .message("Доступ запрещён")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoSuchElement(NoSuchElementException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidation(MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Ошибка валидации");

        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionName(e.getClass().getSimpleName())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleRuntime(RuntimeException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAll(Exception e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .exceptionName(e.getClass().getSimpleName())
                .message("Внутренняя ошибка сервера")
                .timestamp(LocalDateTime.now())
                .build();
    }
}