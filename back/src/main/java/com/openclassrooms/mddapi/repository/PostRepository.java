package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByAuthor(User author);

    List<Post> findByTopic(Topic topic);

    List<Post> findByTopicInOrderByCreatedAtDesc(Collection<Topic> topics);

    List<Post> findByTopicInOrderByCreatedAtAsc(Collection<Topic> topics);

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByCreatedAtAsc();
}