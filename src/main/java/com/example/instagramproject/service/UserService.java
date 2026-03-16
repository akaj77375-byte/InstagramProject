package com.example.instagramproject.service;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.UserProfileResponse;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {

    SimpleResponse updateProfile(Long userId, SignUpRequest request);

    SimpleResponse deleteUser(Long userId);

    UserProfileResponse userProfile(Long userId);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserProfileResponse getUserById(Long userId);

    List<UserResponse> search(String keyword);
}
