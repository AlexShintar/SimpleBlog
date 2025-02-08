package ru.shintar.blog.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Like;
import ru.shintar.blog.entity.Post;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPost(Post post);
    @Transactional
    @Modifying
    void deleteByPostId(Long postId);
}
