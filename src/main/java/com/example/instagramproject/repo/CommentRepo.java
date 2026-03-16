package com.example.instagramproject.repo;

import com.example.instagramproject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepo extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);}
