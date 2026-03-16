package com.example.instagramproject.service.impl;

import com.example.instagramproject.config.jwt.JwtService;
import com.example.instagramproject.dto.auth.request.SignInRequest;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.auth.response.AuthResponse;
import com.example.instagramproject.entity.Follower;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.entity.UserInfo;
import com.example.instagramproject.enums.Role;
import com.example.instagramproject.exception.AlreadyExistsException;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
   private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new AlreadyExistsException("Email " + request.email() + " уже занят!");
        }
        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username " + request.username() + " уже занят!");
        }

        if (!request.phoneNumber().startsWith("+996")) {
            throw new RuntimeException("Номер телефона должен начинаться с +996!");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(Role.USER)
                .build();

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setSubscribers(new ArrayList<>());
        follower.setSubscriptions(new ArrayList<>());
        user.setFollower(follower);

        UserInfo userInfo = UserInfo.builder()
                .fullName(request.fullName())
                .user(user)
                .build();
        user.setUserInfo(userInfo);

        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        User user = userRepo.getUserByEmail(request.email()).orElseThrow(() ->
                new NoSuchElementException("Пользователь не найден"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль!");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }
}
