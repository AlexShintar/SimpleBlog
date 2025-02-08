package ru.shintar.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.entity.Comment;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.CommentRepository;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment addComment(Post post, String content) {

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, String newContent) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Комментарий не найден");
        }
        commentRepository.deleteById(id);
    }

    public int getCommentCount(Post post) {
        return commentRepository.countByPost(post);
    }
    @Transactional
    public void deleteCommentsByPostId(Long id) {
        commentRepository.deleteByPostId(id);
    }
}
