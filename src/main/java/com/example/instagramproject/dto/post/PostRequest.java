package com.example.instagramproject.dto.post;

public record PostRequest(
        String title,
        String description,
        String imageUrl
) {}