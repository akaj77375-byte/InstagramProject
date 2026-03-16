package com.example.instagramproject.service;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.post.PostRequest;
import com.example.instagramproject.dto.post.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    SimpleResponse savePost(PostRequest postRequest);
    SimpleResponse updatePost(Long postId, PostRequest postRequest);
    SimpleResponse deletePost(Long postId);
    PostResponse getPostById(Long postId);
    Page<PostResponse> getAllPosts(Long userId, Pageable pageable);}