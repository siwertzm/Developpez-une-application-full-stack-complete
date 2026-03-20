package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(Authentication authentication) {
        return ResponseEntity.ok(topicService.getAllTopics(authentication.getName()));
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable UUID id, Authentication authentication) {
        topicService.subscribe(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/subscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable UUID id, Authentication authentication) {
        topicService.unsubscribe(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }
}