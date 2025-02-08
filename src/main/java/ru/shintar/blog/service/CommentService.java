package ru.shintar.blog.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.entity.Comment;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.CommentRepository;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

    @Transactional
    public void addComment(Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Пост не найден"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Transactional
    public Long updateComment(Long id, String newContent) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return comment.getPost().getId();
    }

    @Transactional
    public Long deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        Long postId = comment.getPost().getId();
        commentRepository.deleteById(id);
        return postId;
    }

    public int getCommentCount(Post post) {
        return commentRepository.countByPost(post);
    }
    @Transactional
    public void deleteCommentsByPostId(Long id) {
        commentRepository.deleteByPostId(id);
    }
}
