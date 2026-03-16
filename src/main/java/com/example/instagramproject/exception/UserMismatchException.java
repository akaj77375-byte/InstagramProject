package com.example.instagramproject.exception;

public class UserMismatchException extends RuntimeException {
    public UserMismatchException(String message) {
        super(message);
    }
}
