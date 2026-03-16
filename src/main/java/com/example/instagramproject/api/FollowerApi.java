package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.follow.UserSearchResponse;
import com.example.instagramproject.dto.user.UserResponse;
import com.example.instagramproject.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/followers")
@RequiredArgsConstructor
public class FollowerApi {

    private final FollowerService followerService;

    @PostMapping("/follow/{targetUserId}")
    public SimpleResponse follow(@PathVariable Long targetUserId) {
        return followerService.followUser(targetUserId);
    }

    @GetMapping("/subscribers/{userId}")
    public Page<UserResponse> getAllSubscribers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return followerService.getAllSubscribers(userId, pageable);
    }

    @GetMapping("/subscriptions/{userId}")
    public Page<UserResponse> getAllSubscriptions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size);
        return followerService.getAllSubscriptions(userId, pageable);
    }

    @GetMapping("/search")
    public List<UserSearchResponse> searchUsers(@RequestParam String query) {
        return followerService.searchUsers(query);
    }
}