package com.example.instagramproject.dto.post;

import lombok.Builder;

@Builder
public record PostResponse(
        Long id,
        String imageURL,
        String description,
        int likeCount
) {
}