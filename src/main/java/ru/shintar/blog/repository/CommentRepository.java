package ru.shintar.blog.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Comment;
import ru.shintar.blog.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    int countByPost(Post post);
    @Transactional
    @Modifying
    void deleteByPostId(Long postId);
}
