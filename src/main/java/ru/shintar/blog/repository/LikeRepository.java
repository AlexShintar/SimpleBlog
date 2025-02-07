package ru.shintar.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
