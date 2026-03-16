package com.example.instagramproject.api;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.userinfo.UserInfoRequest;
import com.example.instagramproject.dto.userinfo.UserInfoResponse;
import com.example.instagramproject.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @PostMapping("/{userId}")
    public SimpleResponse save(
            @PathVariable Long userId,
            @RequestBody @Valid UserInfoRequest userInfoRequest
    ) {
        return userInfoService.saveUserInfo(userId, userInfoRequest);
    }

    @GetMapping("/{userId}")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    @PutMapping("/{userId}")
    public SimpleResponse update(
            @PathVariable Long userId,
            @RequestBody UserInfoRequest request
    ) {
        return userInfoService.update(userId, request);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userInfoService.delete(id);
    }

    @PatchMapping("/image/{userId}")
    public SimpleResponse changeImage(
            @PathVariable Long userId,
            @RequestParam("imageUrl") String newImage
    ) {
        return userInfoService.changeImage(userId, newImage);
    }

    @DeleteMapping("/image/{userId}")
    public SimpleResponse deleteImage(@PathVariable Long userId) {
        return userInfoService.deleteImage(userId);
    }
}