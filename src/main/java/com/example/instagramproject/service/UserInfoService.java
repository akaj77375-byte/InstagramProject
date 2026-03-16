package com.example.instagramproject.service;

import com.example.instagramproject.dto.SimpleResponse;
import com.example.instagramproject.dto.userinfo.UserInfoRequest;
import com.example.instagramproject.dto.userinfo.UserInfoResponse;

public interface UserInfoService {
    SimpleResponse saveUserInfo(Long userId, UserInfoRequest userInfoRequest);
    UserInfoResponse findUserInfoByUserId(Long userId);
    SimpleResponse update(Long userId, UserInfoRequest request);
    SimpleResponse delete(Long id);
    SimpleResponse changeImage(Long userId, String newImage);
    SimpleResponse deleteImage(Long userId);
}
