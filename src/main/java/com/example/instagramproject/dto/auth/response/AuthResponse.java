package com.example.instagramproject.dto.auth.response;

import com.example.instagramproject.enums.Role;
import lombok.Builder;


@Builder
public record AuthResponse(
        String email,
        String token,
        Role role
) {
}
