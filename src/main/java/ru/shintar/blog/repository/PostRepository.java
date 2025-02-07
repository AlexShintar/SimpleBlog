package ru.shintar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
