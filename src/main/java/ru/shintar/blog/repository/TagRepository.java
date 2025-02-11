package ru.shintar.blog.repository;

import ru.shintar.blog.model.Tag;

import java.util.List;


public interface TagRepository {
    List<Tag> findAllByPostId(Long Id);

    void deleteByPostId(Long postId);

    void save(Tag tag);
}
