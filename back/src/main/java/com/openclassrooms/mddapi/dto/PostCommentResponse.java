package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PostCommentResponse {
    private UUID id;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
}