package com.example.instagramproject.repo;

import com.example.instagramproject.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    @Query("SELECT ui FROM UserInfo ui WHERE ui.user.id = :userId")
    Optional<UserInfo> findByUserId(Long userId);

}