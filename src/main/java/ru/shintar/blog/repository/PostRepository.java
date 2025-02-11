package ru.shintar.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.shintar.blog.model.Post;

import java.util.Optional;

public interface PostRepository {

    Page<Post> findByTag(String tag, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);

    Long save(Post post);

    void deleteById(Long id);

    void update(Post post);
}
