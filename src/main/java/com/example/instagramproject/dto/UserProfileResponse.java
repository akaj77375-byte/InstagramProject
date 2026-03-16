package com.example.instagramproject.dto;

import com.example.instagramproject.dto.post.PostResponse;
import lombok.Builder;


import java.util.List;

@Builder
public record UserProfileResponse(
        Long id,
        String username,
        String image,
        String fullName,
        String biography,
        int countSubscribers,
        int countSubscriptions,
        List<PostResponse> posts
) {
}