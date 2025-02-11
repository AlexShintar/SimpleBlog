package ru.shintar.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.model.Comment;
import ru.shintar.blog.model.Post;
import ru.shintar.blog.repository.CommentRepository;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Transactional
    public void addComment(Long postId, String content) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Пост не найден");
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostId(postId);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Transactional
    public Long updateComment(Long id, String newContent) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new RuntimeException("Комментарий не найден");
        }

        Comment comment = commentOpt.get();
        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.update(comment);
        return comment.getPostId();
    }

    @Transactional
    public Long deleteComment(Long id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new RuntimeException("Комментарий не найден");
        }

        Long postId = commentOpt.get().getPostId();
        commentRepository.deleteById(id);
        return postId;
    }

    public int getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    public void deleteCommentsByPostId(Long id) {
        commentRepository.deleteByPostId(id);
    }
}
