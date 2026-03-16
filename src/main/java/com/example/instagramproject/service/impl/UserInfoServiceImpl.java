package com.example.instagramproject.service.impl;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.userinfo.UserInfoRequest;
import com.example.instagramproject.dto.userinfo.UserInfoResponse;
import com.example.instagramproject.entity.User;
import com.example.instagramproject.entity.UserInfo;
import com.example.instagramproject.enums.Gender;
import com.example.instagramproject.repo.UserInfoRepo;
import com.example.instagramproject.repo.UserRepo;
import com.example.instagramproject.service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepo userRepo;
    private final UserInfoRepo userInfoRepo;

    @Override
    public SimpleResponse saveUserInfo(Long userId, UserInfoRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUser(user);
        }

        userInfo.setFullName(request.fullName());
        userInfo.setBiography(request.biography());
        userInfo.setImage(request.image());
        if (request.gender() != null) {
            userInfo.setGender(Gender.valueOf(request.gender().toUpperCase()));
        }

        userInfoRepo.save(userInfo);
        return SimpleResponse.builder()
                .message("UserInfo успешно сохранено")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        UserInfo info = user.getUserInfo();
        return new UserInfoResponse(
                info.getId(),
                info.getFullName(),
                info.getBiography(),
                info.getGender() != null ? info.getGender().name() : null,
                info.getImage()
        );
    }

    @Override
    public SimpleResponse update(Long userId, UserInfoRequest request) {
        return saveUserInfo(userId, request);
    }

    @Override
    public SimpleResponse delete(Long id) {
        UserInfo userInfo = userInfoRepo.findById(id).orElseThrow();
        userInfo.getUser().setUserInfo(null);
        userInfoRepo.delete(userInfo);
        return SimpleResponse.builder()
                .message("UserInfo удалено")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse changeImage(Long userId, String newImage) {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserInfo не найден"));

        info.setImage(newImage);
        return new SimpleResponse("Аватарка обновлена", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteImage(Long userId) {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserInfo не найден"));

        info.setImage(null);
        return new SimpleResponse("Аватарка удалена", HttpStatus.OK);
    }
}