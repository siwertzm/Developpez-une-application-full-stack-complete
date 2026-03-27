package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private UUID id;
    private String title;
    private String content;
    private String topicName;
    private String authorUsername;
    private LocalDateTime createdAt;
    private List<PostCommentResponse> comments;
}