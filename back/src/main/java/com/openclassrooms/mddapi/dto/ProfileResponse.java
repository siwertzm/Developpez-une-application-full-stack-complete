package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProfileResponse {
    private UUID id;
    private String username;
    private String email;
    private Set<String> subscriptions;
}