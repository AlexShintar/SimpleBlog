package ru.shintar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Like;
import ru.shintar.blog.entity.Post;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByPost(Post post);
}
