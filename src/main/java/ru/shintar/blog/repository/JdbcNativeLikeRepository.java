package ru.shintar.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.model.Like;
@RequiredArgsConstructor
@Repository
public class JdbcNativeLikeRepository implements LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteByPostId(Long postId) {
        String sql = "DELETE FROM likes WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    public void save(Like like) {
        String sql = "INSERT INTO likes (post_id) VALUES (?)";
        jdbcTemplate.update(sql, like.getPostId());
    }

    @Override
    public int countByPostId(Long postId) {
        String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }
}
