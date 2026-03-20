package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.PostComment;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostCommentRepository extends JpaRepository<PostComment, UUID> {

    List<PostComment> findByPostOrderByCreatedAtAsc(Post post);

    List<PostComment> findByAuthor(User author);
}