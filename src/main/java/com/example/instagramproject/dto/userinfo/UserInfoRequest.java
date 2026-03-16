package com.example.instagramproject.dto.userinfo;

public record UserInfoRequest(
        String fullName,
        String biography,
        String gender,
        String image
) {}