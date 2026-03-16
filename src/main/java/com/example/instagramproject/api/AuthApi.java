package com.example.instagramproject.api;

import com.example.instagramproject.dto.auth.request.SignInRequest;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.auth.response.AuthResponse;
import com.example.instagramproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/signin")
    public AuthResponse signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}