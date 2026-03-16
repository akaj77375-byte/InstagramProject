package com.example.instagramproject.service;


import com.example.instagramproject.dto.SimpleResponse;

public interface LikeService {
        SimpleResponse toggleLikePost(Long postId);
        SimpleResponse toggleLikeComment(Long commentId);
    }

