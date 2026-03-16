package com.example.instagramproject.dto.auth.request;

public record SignInRequest(
        String email,
        String password
) {
}
