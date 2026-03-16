package com.example.instagramproject.service.impl;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.UserProfileResponse;
import com.example.instagramproject.dto.auth.request.SignUpRequest;
import com.example.instagramproject.dto.post.PostResponse;
import com.example.instagramproject.dto.user.UserResponse;
import com.example.instagramproject.entity.Post;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.entity.UserInfo;
import com.example.instagramproject.repo.PostRepo;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final PostRepo postRepo;

    @Override
    public SimpleResponse updateProfile(Long userId, SignUpRequest request) {

        User userToUpdate = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с id " + userId + " не найден"));

        String currentUserEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!userToUpdate.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете редактировать чужой профиль!");
        }

        if (userRepo.existsByEmail(request.email())
                && !userToUpdate.getEmail().equals(request.email())) {
            throw new RuntimeException("Email уже используется");
        }

        userToUpdate.setUsername(request.username());
        userToUpdate.setEmail(request.email());

        if (request.password() != null && !request.password().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(request.password()));
        }

        if (userToUpdate.getUserInfo() != null) {
            userToUpdate.getUserInfo().setFullName(request.fullName());
        }

        userRepo.save(userToUpdate);

        return SimpleResponse.builder()
                .message("Профиль успешно обновлен")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {

        User userToDelete = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        String currentUserEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!userToDelete.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете удалить чужой аккаунт!");
        }

        userRepo.delete(userToDelete);

        return SimpleResponse.builder()
                .message("Аккаунт успешно удален")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public UserProfileResponse userProfile(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        UserInfo info = user.getUserInfo();

        Page<Post> posts = postRepo.findAllByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, 9));

        List<PostResponse> userPosts = posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                        post.getDescription(),
                        post.getLikes() != null ? post.getLikes().size() : 0
                ))
                .toList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(info != null ? info.getFullName() : null)
                .biography(info != null ? info.getBiography() : null)
                .image(info != null ? info.getImage() : null)
                .countSubscribers(user.getFollower() != null ? user.getFollower().getSubscribers().size() : 0)
                .countSubscriptions(user.getFollower() != null ? user.getFollower().getSubscriptions().size() : 0)
                .posts(userPosts)
                .build();
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return userRepo.findAll(pageable)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getUserInfo() != null ? user.getUserInfo().getImage() : null,
                        user.getFollower() != null ? user.getFollower().getSubscribers().size() : 0,
                        user.getFollower() != null ? user.getFollower().getSubscriptions().size() : 0,
                        user.getUserInfo() != null ? user.getUserInfo().getFullName() : null
                ));
    }

    @Override
    public UserProfileResponse getUserById(Long userId) {
        return userProfile(userId);
    }

    @Override
    public List<UserResponse> search(String keyword) {

        return userRepo.searchUsers(keyword)
                .stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ))
                .toList();
    }
}