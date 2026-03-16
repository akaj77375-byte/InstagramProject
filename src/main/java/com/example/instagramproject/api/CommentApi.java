package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.comment.CommentRequest;
import com.example.instagramproject.dto.comment.CommentResponse;
import com.example.instagramproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public SimpleResponse saveComment(@PathVariable Long postId,
                                      @RequestBody CommentRequest request) {
        return commentService.saveComment(postId, request);
    }

    @GetMapping("/post/{postId}")
    public Page<CommentResponse> findAllByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size);
        return commentService.findAllByPostId(postId, pageable);
    }

    @DeleteMapping("/{commentId}")
    public SimpleResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    public SimpleResponse updateComment(@PathVariable Long commentId,
                                        @RequestBody CommentRequest request) {
        return commentService.updateComment(commentId, request);
    }
}