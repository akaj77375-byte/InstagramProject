package com.example.instagramproject.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SimpleResponse(String message, HttpStatus status) {
}
