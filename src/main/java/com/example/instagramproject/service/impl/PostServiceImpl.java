package com.example.instagramproject.service.impl;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.post.PostRequest;
import com.example.instagramproject.dto.post.PostResponse;
import com.example.instagramproject.entity.Image;
import com.example.instagramproject.entity.Post;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.exception.NotFoundException;
import com.example.instagramproject.exception.UserMismatchException;
import com.example.instagramproject.repo.ImageRepo;
import com.example.instagramproject.repo.PostRepo;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;

    @Override
    public SimpleResponse savePost(PostRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();

        Post post = new Post();
        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        if (request.imageUrl() == null || request.imageUrl().isBlank()) {
            throw new RuntimeException("У поста должна быть картинка!");
        }

        Image image = new Image();
        image.setImageURL(request.imageUrl());
        image.setPost(post);

        post.setImages(List.of(image));

        postRepo.save(post);
        return SimpleResponse.builder()
                .message("Пост успешно опубликован")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете редактировать чужой пост!");
        }

        post.setTitle(request.title());
        post.setDescription(request.description());

        if (!post.getImages().isEmpty()) {
            post.getImages().get(0).setImageURL(request.imageUrl());
        }

        postRepo.save(post);
        return SimpleResponse.builder()
                .message("Пост обновлен")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("Пост с id " + postId + " не найден"));

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            throw new UserMismatchException("Вы не можете удалить чужой пост!");
        }

        postRepo.delete(post);
        return SimpleResponse.builder()
                .message("Пост удален")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public PostResponse getPostById(Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        return new PostResponse(
                post.getId(),
                post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                post.getDescription(),
                post.getLikes() != null ? post.getLikes().size() : 0
        );
    }

    @Override
    public Page<PostResponse> getAllPosts(Long userId, Pageable pageable) {
        return postRepo.findAllByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                        post.getDescription(),
                        post.getLikes() != null ? post.getLikes().size() : 0
                ));
    }

}