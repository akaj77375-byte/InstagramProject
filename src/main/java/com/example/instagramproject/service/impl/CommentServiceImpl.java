package com.example.instagramproject.service.impl;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.comment.CommentRequest;
import com.example.instagramproject.dto.comment.CommentResponse;
import com.example.instagramproject.entity.Comment;
import com.example.instagramproject.entity.Post;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.repo.CommentRepo;
import com.example.instagramproject.repo.PostRepo;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse saveComment(Long postId, CommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByEmail(email).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow(() -> new NoSuchElementException("Пост не найден"));

        Comment comment = new Comment();
        comment.setComment(request.comment());
        comment.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        comment.setUser(user);
        comment.setPost(post);

        commentRepo.save(comment);

        return SimpleResponse.builder()
                .message("Комментарий успешно добавлен")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Page<CommentResponse> findAllByPostId(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepo.findAllByPostId(postId, pageable);

        return comments.map(c -> new CommentResponse(
                c.getId(),
                c.getComment(),
                c.getCreatedAt().atStartOfDay(),
                c.getUser().getUsername(),
                c.getLikes() != null ? c.getLikes().size() : 0
        ));
    }

    @Override
    public SimpleResponse deleteComment(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден"));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Вы не можете удалить чужой комментарий!");
        }

        commentRepo.delete(comment);

        return SimpleResponse.builder()
                .message("Комментарий удален")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse updateComment(Long commentId, CommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден"));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Вы не можете редактировать чужой комментарий!");
        }

        comment.setComment(request.comment());

        commentRepo.save(comment);

        return SimpleResponse.builder()
                .message("Комментарий успешно обновлен")
                .status(HttpStatus.OK)
                .build();
    }
}
