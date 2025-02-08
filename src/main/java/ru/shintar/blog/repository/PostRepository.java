package ru.shintar.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN Tag t ON p.id = t.post.id WHERE t.name = :tag")
    Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);


}
