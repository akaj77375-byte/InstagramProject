package com.example.instagramproject.service;


import com.example.instagramproject.dto.auth.request.SignInRequest;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.auth.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);
}
