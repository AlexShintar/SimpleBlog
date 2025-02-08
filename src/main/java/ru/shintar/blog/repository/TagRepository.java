package ru.shintar.blog.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.entity.Tag;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByPost(Post post);

    @Transactional
    @Modifying
    void deleteByPostId(@Param("postId") Long postId);
}
