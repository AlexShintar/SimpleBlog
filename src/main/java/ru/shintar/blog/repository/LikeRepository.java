package ru.shintar.blog.repository;

import ru.shintar.blog.model.Like;

public interface LikeRepository {
    void deleteByPostId(Long postId);
    void save(Like like);
    int countByPostId(Long postId);
}
