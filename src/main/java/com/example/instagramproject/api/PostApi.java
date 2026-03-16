package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.post.PostRequest;
import com.example.instagramproject.dto.post.PostResponse;
import com.example.instagramproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @PostMapping
    public SimpleResponse save(@RequestBody PostRequest request) {
        return postService.savePost(request);
    }

    @GetMapping("/{postId}")
    public PostResponse getById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public SimpleResponse update(@PathVariable Long postId,
                                 @RequestBody PostRequest request) {
        return postService.updatePost(postId, request);
    }

    @DeleteMapping("/{postId}")
    public SimpleResponse delete(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/user/{userId}")
    public Page<PostResponse> getAllPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size);
        return postService.getAllPosts(userId, pageable);
    }
}