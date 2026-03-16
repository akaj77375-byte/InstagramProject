package com.example.instagramproject.service;


import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.follow.UserSearchResponse;
import com.example.instagramproject.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowerService {
    SimpleResponse followUser(Long targetUserId);
    Page<UserResponse> getAllSubscribers(Long userId, Pageable pageable);
    Page<UserResponse> getAllSubscriptions(Long userId,Pageable pageable);
    List<UserSearchResponse> searchUsers(String query);

}

