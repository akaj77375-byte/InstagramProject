package com.example.instagramproject.repo;

import com.example.instagramproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
