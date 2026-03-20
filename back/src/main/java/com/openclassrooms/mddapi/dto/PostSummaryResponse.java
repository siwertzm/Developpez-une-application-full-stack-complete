package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {
    private UUID id;
    private String title;
    private String topicName;
    private String authorUsername;
    private LocalDateTime createdAt;
    private String content;
}