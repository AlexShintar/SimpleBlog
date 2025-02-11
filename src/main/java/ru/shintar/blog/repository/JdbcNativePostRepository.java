package ru.shintar.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.shintar.blog.model.Post;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setImageUrl(rs.getString("image_url"));
        return post;
    };

    @Override
    public Page<Post> findByTag(String tag, Pageable pageable) {

        String sql = "SELECT p.* FROM posts p " +
                "JOIN tags t ON p.id = t.post_id " +
                "WHERE t.name LIKE ? LIMIT ? OFFSET ?";
        List<Post> posts = jdbcTemplate.query(sql, postRowMapper, "%" + tag + "%",
                pageable.getPageSize(), pageable.getOffset());

        String countSql = "SELECT COUNT(*) FROM posts p " +
                "JOIN tags t ON p.id = t.post_id " +
                "WHERE t.name LIKE ?";
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, "%" + tag + "%");

        return new PageImpl<>(posts, pageable, total);
    }
    @Override
    public Page<Post> findAll(Pageable pageable) {

        String sql = "SELECT * FROM posts LIMIT ? OFFSET ?";
        List<Post> posts = jdbcTemplate.query(sql, postRowMapper, pageable.getPageSize(), pageable.getOffset());

        String countSql = "SELECT COUNT(*) FROM posts";
        int total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        Optional<Post> postOpt = jdbcTemplate.query(sql, postRowMapper, id).stream().findFirst();
        return postOpt;
    }

    @Override
    public Long save(Post post) {
        String sql = "INSERT INTO posts (title, content, image_url) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getImageUrl());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getImageUrl(), post.getId());
    }
}

