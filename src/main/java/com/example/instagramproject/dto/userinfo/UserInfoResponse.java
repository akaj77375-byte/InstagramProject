package com.example.instagramproject.dto.userinfo;

public record UserInfoResponse(
        Long id,
        String fullName,
        String biography,
        String gender,
        String image
) {}