package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
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
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public List<TopicResponse> getAllTopics(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return topicRepository.findAll()
            .stream()
            .map(topic -> new TopicResponse(
                topic.getId(),
                topic.getName(),
                topic.getDescription(),
                user.getSubscriptions().contains(topic)
            ))
            .collect(Collectors.toList());
    }

    public void subscribe(String username, @NonNull UUID topicId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Thème introuvable"));

        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    public void unsubscribe(String username, @NonNull UUID topicId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Thème introuvable"));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }
}