package ru.shintar.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.model.Comment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcNativeCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> new Comment(
            rs.getLong("id"),
            rs.getString("content"),
            rs.getLong("post_id"),
            rs.getTimestamp("updated_at").toLocalDateTime()
    );

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        String sql = "SELECT * FROM comments WHERE post_id = ?";
        return jdbcTemplate.query(sql, commentRowMapper, postId);
    }

    @Override
    public int countByPostId(Long postId) {
        String sql = "SELECT COUNT(*) FROM comments WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        String sql = "DELETE FROM comments WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        return jdbcTemplate.query(sql, commentRowMapper, id).stream().findFirst();
    }

    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO comments (content, post_id, updated_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, comment.getContent(), comment.getPostId(), comment.getUpdatedAt());
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET content = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getUpdatedAt(), comment.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}