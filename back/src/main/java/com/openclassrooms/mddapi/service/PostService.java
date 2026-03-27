package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.PostComment;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.PostCommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public List<PostSummaryResponse> getFeed(String username, String sort) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<Post> posts;

        if ("asc".equalsIgnoreCase(sort)) {
            posts = postRepository.findByTopicInOrderByCreatedAtAsc(user.getSubscriptions());
        } else {
            posts = postRepository.findByTopicInOrderByCreatedAtDesc(user.getSubscriptions());
        }

        return posts.stream()
                .map(post -> new PostSummaryResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getTopic().getName(),
                        post.getAuthor().getUsername(),
                        post.getCreatedAt(),
                        post.getContent()))
                .collect(Collectors.toList());
    }

    public PostDetailResponse getPostById(@NonNull UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article introuvable"));

        List<PostCommentResponse> comments = postCommentRepository.findByPostOrderByCreatedAtAsc(post)
                .stream()
                .map(comment -> new PostCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor().getUsername(),
                        comment.getCreatedAt()))
                .collect(Collectors.toList());

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTopic().getName(),
                post.getAuthor().getUsername(),
                post.getCreatedAt(),
                comments);
    }

    public PostDetailResponse createPost(String username, CreatePostRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        UUID topicId = request.getTopicId();
        if (topicId == null) {
            throw new IllegalArgumentException("TopicId ne peut pas être null");
        }
        
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème introuvable"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(user);
        post.setTopic(topic);

        Post savedPost = postRepository.save(post);

        return new PostDetailResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getTopic().getName(),
                savedPost.getAuthor().getUsername(),
                savedPost.getCreatedAt(),
                List.of());
    }

    public PostCommentResponse addComment(String username, @NonNull UUID postId, CreateCommentRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article introuvable"));

        PostComment comment = new PostComment();
        comment.setContent(request.getContent());
        comment.setAuthor(user);
        comment.setPost(post);

        PostComment savedComment = postCommentRepository.save(comment);

        return new PostCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getAuthor().getUsername(),
                savedComment.getCreatedAt());
    }
}