package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostSummaryResponse>> getFeed(
        Authentication authentication,
        @RequestParam(defaultValue = "desc") String sort
    ) {
        return ResponseEntity.ok(postService.getFeed(authentication.getName(), sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getPostById(@PathVariable @NonNull UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostDetailResponse> createPost(
        Authentication authentication,
        @Valid @RequestBody CreatePostRequest request
    ) {
        return ResponseEntity.ok(postService.createPost(authentication.getName(), request));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<PostCommentResponse> addComment(
        Authentication authentication,
        @PathVariable @NonNull UUID id,
        @Valid @RequestBody CreateCommentRequest request
    ) {
        return ResponseEntity.ok(postService.addComment(authentication.getName(), id, request));
    }
}