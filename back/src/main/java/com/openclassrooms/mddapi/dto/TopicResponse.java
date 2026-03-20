package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TopicResponse {
    private UUID id;
    private String name;
    private String description;
    private boolean subscribed;
}