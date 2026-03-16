package com.example.instagramproject.repo;

import com.example.instagramproject.dto.follow.UserSearchResponse;
import com.example.instagramproject.dto.follow.UserShortResponse;
import com.example.instagramproject.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Optional<Follower> findByUserId(Long userId);


    @Query("SELECT new com.example.instagramproject.dto.follow.UserSearchResponse(u.id, u.username, ui.image, ui.fullName) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.username ILIKE %:query% OR ui.fullName ILIKE %:query%")
    List<UserSearchResponse> searchUsers(String query);

    @Query("SELECT new com.example.instagramproject.dto.follow.UserShortResponse(u.id, u.username, ui.image) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.id IN (SELECT sub FROM Follower f JOIN f.subscribers sub WHERE f.user.id = :userId)")
    List<UserShortResponse> findSubscribersByUserId(Long userId);

    @Query("SELECT new com.example.instagramproject.dto.follow.UserShortResponse(u.id, u.username, ui.image) " +
            "FROM User u JOIN u.userInfo ui " +
            "WHERE u.id IN (SELECT sub FROM Follower f JOIN f.subscriptions sub WHERE f.user.id = :userId)")
    List<UserShortResponse> findSubscriptionsByUserId(Long userId);
}