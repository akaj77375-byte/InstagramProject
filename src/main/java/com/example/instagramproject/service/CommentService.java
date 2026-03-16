package com.example.instagramproject.service;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.comment.CommentRequest;
import com.example.instagramproject.dto.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    SimpleResponse saveComment(Long postId, CommentRequest request);
    Page<CommentResponse> findAllByPostId(Long postId, Pageable pageable);
    SimpleResponse deleteComment(Long commentId);
    SimpleResponse updateComment(Long commentId, CommentRequest request);
}