package com.example.instagramproject.service.impl;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.follow.UserSearchResponse;
import com.example.instagramproject.dto.user.UserResponse;
import com.example.instagramproject.entity.Follower;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.repo.FollowerRepo;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.FollowerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepo followerRepo;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse followUser(Long targetUserId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email).orElseThrow();

        if (currentUser.getId().equals(targetUserId)) {
            throw new RuntimeException("Нельзя подписаться на самого себя!");
        }

        User targetUser = userRepo.findById(targetUserId).orElseThrow();

        Follower myFollowerInfo = currentUser.getFollower();
        Follower targetFollowerInfo = targetUser.getFollower();

        if (myFollowerInfo.getSubscriptions().contains(targetUserId)) {
            myFollowerInfo.getSubscriptions().remove(targetUserId);
            targetFollowerInfo.getSubscribers().remove(currentUser.getId());
            return new SimpleResponse("Отписка выполнена", HttpStatus.OK);
        } else {
            myFollowerInfo.getSubscriptions().add(targetUserId);
            targetFollowerInfo.getSubscribers().add(currentUser.getId());
            return new SimpleResponse("Подписка выполнена", HttpStatus.OK);
        }
    }

    @Override
    public Page<UserResponse> getAllSubscribers(Long userId, Pageable pageable) {
        Follower follower = followerRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Follower info not found"));

        return userRepo.findAllByIdIn(follower.getSubscribers(), pageable)
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ));
    }

    @Override
    public Page<UserResponse> getAllSubscriptions(Long userId, Pageable pageable) {
        Follower follower = followerRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Follower info not found"));

        return userRepo.findAllByIdIn(follower.getSubscriptions(), pageable)
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ));
    }

    @Override
    public List<UserSearchResponse> searchUsers(String query) {
        return followerRepo.searchUsers(query);
    }
}