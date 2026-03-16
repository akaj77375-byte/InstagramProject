package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeApi {
    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public SimpleResponse likePost(@PathVariable Long postId) {
        return likeService.toggleLikePost(postId);
    }

    @PostMapping("/comment/{commentId}")
    public SimpleResponse likeComment(@PathVariable Long commentId) {
        return likeService.toggleLikeComment(commentId);
    }
}
