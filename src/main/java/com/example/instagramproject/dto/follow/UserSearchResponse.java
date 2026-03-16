package com.example.instagramproject.dto.follow;

public record UserSearchResponse(
        Long id,
        String username,
        String image,
        String fullName
) {}
