package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TopicService topicService;

    // =========================
    // GET ALL TOPICS
    // =========================

    @Test
    void shouldReturnAllTopicsWithSubscriptionStatus() {
        // Arrange
        String username = "testuser";

        Topic topic1 = new Topic();
        topic1.setId(UUID.randomUUID());
        topic1.setName("Java");
        topic1.setDescription("Langage Java");

        Topic topic2 = new Topic();
        topic2.setId(UUID.randomUUID());
        topic2.setName("Spring");
        topic2.setDescription("Framework Spring");

        User user = new User();
        user.setUsername(username);
        user.setSubscriptions(new HashSet<>(List.of(topic1))); // abonné à topic1 uniquement

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(topicRepository.findAll()).thenReturn(List.of(topic1, topic2));

        // Act
        List<TopicResponse> result = topicService.getAllTopics(username);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        TopicResponse r1 = result.get(0);
        TopicResponse r2 = result.get(1);

        assertEquals("Java", r1.getName());
        assertTrue(r1.isSubscribed());

        assertEquals("Spring", r2.getName());
        assertFalse(r2.isSubscribed());

        verify(userRepository).findByUsername(username);
        verify(topicRepository).findAll();
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundInGetAllTopics() {
        // Arrange
        String username = "unknown";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UsernameNotFoundException.class, () ->
                topicService.getAllTopics(username)
        );

        verify(userRepository).findByUsername(username);
        verify(topicRepository, never()).findAll();
    }

    // =========================
    // SUBSCRIBE
    // =========================

    @Test
    void shouldSubscribeUserToTopic() {
        // Arrange
        String username = "testuser";
        UUID topicId = UUID.randomUUID();

        Topic topic = new Topic();
        topic.setId(topicId);

        User user = new User();
        user.setUsername(username);
        user.setSubscriptions(new HashSet<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        // Act
        topicService.subscribe(username, topicId);

        // Assert
        assertTrue(user.getSubscriptions().contains(topic));

        verify(userRepository).findByUsername(username);
        verify(topicRepository).findById(topicId);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundInSubscribe() {
        // Arrange
        String username = "unknown";
        UUID topicId = UUID.randomUUID();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UsernameNotFoundException.class, () ->
                topicService.subscribe(username, topicId)
        );

        verify(userRepository).findByUsername(username);
        verify(topicRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenTopicNotFoundInSubscribe() {
        // Arrange
        String username = "testuser";
        UUID topicId = UUID.randomUUID();

        User user = new User();
        user.setUsername(username);
        user.setSubscriptions(new HashSet<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                topicService.subscribe(username, topicId)
        );

        verify(userRepository).findByUsername(username);
        verify(topicRepository).findById(topicId);
        verify(userRepository, never()).save(any());
    }

    // =========================
    // UNSUBSCRIBE
    // =========================

    @Test
    void shouldUnsubscribeUserFromTopic() {
        // Arrange
        String username = "testuser";
        UUID topicId = UUID.randomUUID();

        Topic topic = new Topic();
        topic.setId(topicId);

        User user = new User();
        user.setUsername(username);
        user.setSubscriptions(new HashSet<>(List.of(topic)));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        // Act
        topicService.unsubscribe(username, topicId);

        // Assert
        assertFalse(user.getSubscriptions().contains(topic));

        verify(userRepository).findByUsername(username);
        verify(topicRepository).findById(topicId);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundInUnsubscribe() {
        // Arrange
        String username = "unknown";
        UUID topicId = UUID.randomUUID();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UsernameNotFoundException.class, () ->
                topicService.unsubscribe(username, topicId)
        );

        verify(userRepository).findByUsername(username);
        verify(topicRepository, never()).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenTopicNotFoundInUnsubscribe() {
        // Arrange
        String username = "testuser";
        UUID topicId = UUID.randomUUID();

        User user = new User();
        user.setUsername(username);
        user.setSubscriptions(new HashSet<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                topicService.unsubscribe(username, topicId)
        );

        verify(userRepository).findByUsername(username);
        verify(topicRepository).findById(topicId);
        verify(userRepository, never()).save(any());
    }
}