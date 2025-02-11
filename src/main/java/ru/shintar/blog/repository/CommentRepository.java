package ru.shintar.blog.repository;

import ru.shintar.blog.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAllByPostId(Long postId);

    int countByPostId(Long postId);

    void deleteByPostId(Long postId);

    Optional<Comment> findById(Long id);

    void save(Comment comment);

    void update(Comment comment);

    void deleteById(Long id);
}