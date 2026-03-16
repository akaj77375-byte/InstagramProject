package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.UserProfileResponse;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.user.UserResponse;
import com.example.instagramproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping
    public Page<UserResponse> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size);
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    public UserProfileResponse getProfile(@PathVariable Long userId) {
        return userService.userProfile(userId);
    }

    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId,
                                 @RequestBody SignUpRequest request) {
        return userService.updateProfile(userId, request);
    }

    @DeleteMapping("/{userId}")
    public SimpleResponse delete(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}