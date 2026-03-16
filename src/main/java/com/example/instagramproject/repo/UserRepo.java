package com.example.instagramproject.repo;

import com.example.instagramproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User>  getUserByEmail(String email);
    Page<User> findAllByIdIn(List<Long> ids, Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.userInfo ui WHERE u.username ILIKE %:keyword% OR ui.fullName ILIKE %:keyword%")
    List<User> searchUsers(String keyword);
}
