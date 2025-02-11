package ru.shintar.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.shintar.blog.model.Tag;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcNativeTagRepository implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Tag> tagRowMapper = (rs, rowNum) -> {
        Tag tag = new Tag();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        tag.setPostId(rs.getLong("post_id"));
        return tag;
    };

    @Override
    public List<Tag> findAllByPostId(Long postId) {
        String sql = "SELECT * FROM tags WHERE post_id = ?";
        return jdbcTemplate.query(sql, tagRowMapper, postId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        String sql = "DELETE FROM tags WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    public void save(Tag tag) {
        String sql = "INSERT INTO tags (name, post_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, tag.getName(), tag.getPostId());
    }
}